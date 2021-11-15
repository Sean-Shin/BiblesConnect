package sean.to.readbiblesmart.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import sean.to.readbiblesmart.CustomAdapter;
import sean.to.readbiblesmart.MainActivity;
import sean.to.readbiblesmart.R;
import sean.to.readbiblesmart.data.BibleData;
import sean.to.readbiblesmart.data.BibleModel;
import sean.to.readbiblesmart.data.BibleNames;
import sean.to.readbiblesmart.util.BibleUtil;
import sean.to.readbiblesmart.util.ScreenUtil;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<BibleModel> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private static SearchView editsearch;
    private static ListView listView;
    private static String previousQuery;
    ArrayList<String> list;
    ArrayAdapter<String > listadapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        previousQuery = "";
        editsearch = (SearchView) root.findViewById(R.id.searchbar);
        data = new ArrayList<BibleModel>();

//        initBible();

//        listView = (ListView) root.findViewById(R.id.lv1);
//
//        list = new ArrayList<>();
//        list.add("Apple");
//        list.add("Banana");
//        list.add("Pineapple");
//        list.add("Orange");
//        list.add("Lychee");
//        list.add("Gavava");
//        list.add("Peech");
//        list.add("Melon");
//        list.add("Watermelon");
//        list.add("Papaya");
//
//        listadapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_list_item_1,list);
//        listView.setAdapter(listadapter);


        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

//                if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }

                updateBible(query);
//                Toast.makeText(root.getContext(), query, Toast.LENGTH_LONG).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
//                updateBible(newText);
                return false;
            }
        });

        myOnClickListener = new MyOnClickListener(getActivity());

        recyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);

//        recyclerView.setHasFixedSize(true);

//        Log.d("test", getActivity().toString());
//        Log.d("test", recyclerView.toString());
//        Log.d("test", root.toString());
//        Log.d("test", container.toString());

        layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(layoutManager);




//        for (int i = 0; i < BibleData.nameArray.length; i++) {
//            data.add(new BibleModel(
//                    BibleData.nameArray[i],
//                    BibleData.versionArray[i],
//                    BibleData.id_[i]
////                    MyData.drawableArray[i]
//            ));
//        }

//        removedItems = new ArrayList<Integer>();

        todayBible();

        if(!isLoadingDone())
            showLicense();


//        Log.d("test", data.toString());

        adapter = new CustomAdapter(data);
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());



        MainActivity.fragmentManager = getParentFragmentManager();

        getParentFragmentManager().setFragmentResultListener("requestKey", this,
                new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("action");
                if(result != null)
                    changeBible(result);

                String resultForBibleNameIndex =  bundle.getString("bible");
                if(resultForBibleNameIndex != null){
                    String resultForBibleName = BibleData.bibleNames[Integer.parseInt(resultForBibleNameIndex)];
                    String query = resultForBibleName + " 1 1";
                    updateBible(query);
                    updateKeyword(query );
                }
                // Do something with the result
            }
        });

//        setMargin(savedInstanceState);


        return root;
    }
//    private void underlineButton(){
//        Button button = (Button) getActivity().findViewById(R.id.historyBtn);
//        if(button != null)
//            button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//    }
    private void setMargin(Bundle savedInstanceState ){
        final FrameLayout frameLayout = (FrameLayout)getActivity().findViewById(R.id.biblecard);

        if ((frameLayout != null)){
//                && (savedInstanceState == null)) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) frameLayout.getLayoutParams();
            //left, top, right, bottom
            params.setMargins(0, 0, 0, new ScreenUtil().getActionBarHeight(getContext()));
            frameLayout.setLayoutParams(params);
        }
    }
    private void showLicense(){

        String title = getResources().getString(R.string.license_overview);
        String body = getResources().getString(R.string.license);
        BibleModel bm = new BibleModel(title,body);
        data.add(bm);
    }
    public void updateBible(String query){
        updateBible(query,true);
    }
    private void displayBible(String query){
        updateBible(query,false);
    }

    private void updateBible(String query, boolean checksame){

        setMargin(null);

        String[] result = new BibleUtil().parseQuery(query);
        boolean isSamequery = false;
        if(checksame){
           isSamequery =  previousQuery.equals(query);
        }

        if(!isSamequery && result!= null && isLoadingDone()){
//                && result[0] !=null && result[1] != null && result[2] != null) {

//                    BibleModel bm = readJsonFile(result[0], result[1], result[2]);
            if(!new BibleModel().isValidBibleName(result[0])){
                Toast.makeText(this.getContext(), "Input a keyword. ex) Genesis 1 1",Toast.LENGTH_LONG).show();
                return;
            }
            data = ((CustomAdapter)adapter).removeAll(data);
            data = new BibleUtil().readBible(result[0], result[1], result[2], data);
//                    data.add(bm);

            String newQuery = result[0] + " " + result[1] + " " + result[2];
            previousQuery = newQuery;

        }else{
//            Toast.makeText(this.getContext(), "Input a keyword. ex) John 1 1",Toast.LENGTH_LONG).show();
            return;
        }
    }
    public boolean isLoadingDone(){
        if(MainActivity.esvloadingdone && MainActivity.kjvloadingdone && MainActivity.nivloadingdone && MainActivity.nltloadingdone)
            return true;
        return false;
    }
    public void todayBible(){
        //        BibleModel bibleModel = new BibleUtil().readJsonFile("esv", BibleData.bibleNames[0],"1","1");
//        data.add(bibleModel);
    }
    public void nextBible(String pQuery){
        String query = pQuery;
        Log.d("nextBible", query);
        String[] result = new BibleUtil().parseQuery(query);

        if(result == null){
            Toast.makeText(this.getContext(), "Input a keyword. ex) Genesis 1 1",Toast.LENGTH_LONG).show();
            return;
        }
        Integer newIndex = Integer.parseInt(result[2]) + 1;
        int index = new BibleModel().getBibleIndex(result[0],0);

        if(index < 0){
            Toast.makeText(this.getContext(), "Input a keyword. ex) Genesis 1 1",Toast.LENGTH_LONG).show();
            return;
        }
        if(index == 65 &&  result[1].equals("22") && result[2].equals("21")){
            Toast.makeText(this.getContext(), "This is the last versus.",Toast.LENGTH_LONG).show();
            return;
        }

        int lastChapter = new BibleModel().getLastChapter(index);
//                BibleData.bibleKeys[index].ch ;
        int chapter = Integer.parseInt(result[1]);
        int lastVersus = BibleData.verses[index][chapter-1];


        if(newIndex > lastVersus){
            int ch = chapter + 1;
            Log.d("***", " "+ index +","+ch + ","+ lastChapter);
            if(ch > lastChapter){
                result[0] = BibleData.bibleNames[index+1];
                result[1] = "1";
                result[2] = "1";
            }else{
                result[1] = "" + ch;
                result[2] = "1";
            }

        }else{
            result[2] = newIndex.toString();
        }


        data = ((CustomAdapter)adapter).removeAll(data);
        data = new BibleUtil().readBible(result[0], result[1], result[2], data);
//                    data.add(bm);

        String newQuery = result[0] + " " + result[1] + " " + result[2];
        previousQuery = newQuery;
        editsearch.setQuery(newQuery,true);

    }
    public void updateKeyword(String newQuery){
        editsearch.setQuery(newQuery,true);
    }
    public void preBible(String pQuery){
        String query = pQuery;
        Log.d("nextBible", query);
        String[] result = new BibleUtil().parseQuery(query);

        if(result == null){
            Toast.makeText(this.getContext(), "Input a keyword. ex) Genesis 1 1",Toast.LENGTH_LONG).show();
            return;
        }

        Integer newIndex = Integer.parseInt(result[2]) - 1;
        result[2] = newIndex.toString();

        int index = new BibleModel().getBibleIndex(result[0],0);

        if(index < 0){
            Toast.makeText(this.getContext(), "Input a keyword. ex) Genesis 1 1",Toast.LENGTH_LONG).show();
            return;
        }

        if(index == 0 &&  result[1].equals("1") && result[2].equals("0")){
            Toast.makeText(this.getContext(), "This is the first versus.",Toast.LENGTH_LONG).show();
            return;
        }

//                BibleData.bibleKeys[index].ch ;
        int chapter = Integer.parseInt(result[1]);

        if(newIndex <= 0){
            int ch = chapter - 1;
            int lastChapter = new BibleModel().getLastChapter(index-1);
            int lastVersus = BibleData.verses[index-1][lastChapter-1];

            Log.d("***", " "+ index +","+ lastVersus + ","+ lastChapter);
            if(ch <=0 ){
                result[0] = BibleData.bibleNames[index-1];
                result[1] = ""+lastChapter;
                result[2] = ""+lastVersus;
            }else{
                result[1] = "" + ch;
                result[2] = ""+lastVersus;
            }

        }else{
            result[2] = newIndex.toString();
        }

        data = ((CustomAdapter)adapter).removeAll(data);
        data = new BibleUtil().readBible(result[0], result[1], result[2], data);
//                    data.add(bm);

        String newQuery = result[0] + " " + result[1] + " " + result[2];
        previousQuery = newQuery;
        editsearch.setQuery(newQuery,true);
    }
    public  void changeBible(String action){
        changeBible(action, previousQuery);
    }
    public void changeBible(String action, String query){
//            Intent intent = new Intent(context, MainActivity.class);
//            // put "extras" into the bundle for access in the second activity
//            intent.putExtra("action", action);
//            intent.putExtra("query", query);

//            context.startActivity(intent);
        setMargin(null);
        if(action.equals("next")){
            nextBible(query);
        }else if(action.equals("pre")){
            preBible(query);
        }


    }


    public class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        public MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
//            removeItem(v);
//            Log.d("click", " " + v.getId());
//            if(v.getId() == R.id.nextbtn){
//                //Intent
//                changeBible("next", previousQuery);
//
////                Toast.makeText(context, "next",Toast.LENGTH_LONG).show();
//
//            }else if(v.getId() == R.id.prebtn){
//                changeBible("pre", previousQuery);
//
////                Toast.makeText(context, "pre",Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(context, "else",Toast.LENGTH_LONG).show();
//            }
        }




//        private void removeItem(View v) {
//            int selectedItemPosition = recyclerView.getChildPosition(v);
//            RecyclerView.ViewHolder viewHolder
//                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
//            TextView textViewName
//                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
//            String selectedName = (String) textViewName.getText();
//            int selectedItemId = -1;
//            for (int i = 0; i < MyData.nameArray.length; i++) {
//                if (selectedName.equals(MyData.nameArray[i])) {
//                    selectedItemId = MyData.id_[i];
//                }
//            }
//            removedItems.add(selectedItemId);
//            data.remove(selectedItemPosition);
//            adapter.notifyItemRemoved(selectedItemPosition);
//        }
    }
    public void saveBibleRead(String bible){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.previousBible),bible);
        editor.apply();
    }
    public String readBibleLast(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        return sharedPref.getString(getString(R.string.previousBible),"Genesis 1 1");
    }


    @Override
    public void onStart() {
        super.onStart();
        if(previousQuery.length() == 0){
            String query = readBibleLast();
            Log.d("**read",query );
//            displayBible(query);
//            showLicense();
            updateKeyword(query);
        }
        if(editsearch.getQuery().length() == 0){
            showLicense();
        }

//        underlineButton();
    }


    @Override
    public void onDestroy() {
        saveBibleRead(previousQuery);
        Log.d("**save",previousQuery );
        super.onDestroy();
    }
}