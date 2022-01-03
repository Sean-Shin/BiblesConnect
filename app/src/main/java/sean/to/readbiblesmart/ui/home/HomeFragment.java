package sean.to.readbiblesmart.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import sean.to.readbiblesmart.CustomAdapter;
import sean.to.readbiblesmart.MainActivity;
import sean.to.readbiblesmart.NotesActivity;
import sean.to.readbiblesmart.R;
import sean.to.readbiblesmart.data.BibleData;
import sean.to.readbiblesmart.data.BibleModel;
import sean.to.readbiblesmart.data.BibleNames;
import sean.to.readbiblesmart.util.BibleUtil;
import sean.to.readbiblesmart.util.ScreenUtil;
import sean.to.readbiblesmart.util.Util;

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
    private static int startIndex;
    static ArrayList<String> books;

    //    private ActivityResultLauncher<Intent> activityResultLauncher;
    ImageButton imageButton;
    ArrayList<String> list;
    ArrayAdapter<String > listadapter;

//    Timer timer;
//    TimerTask timerTask;
//    final Handler handler = new Handler();

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent data = result.getData();
                String query = data.getExtras().getString("title");
                new Util().printLog("---++++--", "result ok " + query);
                updateKeyword(query);
            }
            new Util().printLog("---++++--", "result ok " + result.getResultCode());
        }
    });

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


        new Util().printLog("-----", "home:onCreateView");
        previousQuery = MainActivity.previousQuery;//"";
        editsearch = (SearchView) root.findViewById(R.id.searchbar);
        data = new ArrayList<BibleModel>();
        books = new ArrayList<String>();

        startIndex = 0;
        readBooksOrder();

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
//                displayBible(query);
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

//        new Util().printLog("test", getActivity().toString());
//        new Util().printLog("test", recyclerView.toString());
//        new Util().printLog("test", root.toString());
//        new Util().printLog("test", container.toString());

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

        if(MainActivity.previousQuery != null && MainActivity.previousQuery.length() > 0){
            updateKeyword(MainActivity.previousQuery);
        }else if(!isLoadingDone())
            showLicense();


//        new Util().printLog("test", data.toString());

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

                String resultForKeywordUpdate = bundle.getString("loadingdone");
                if(resultForKeywordUpdate != null){
                    updateKeyword(MainActivity.previousQuery);
                }
                // Do something with the result
            }
        });

//        setMargin(savedInstanceState);

        guideText();



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);


        return root;
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        0) {
//                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }
    private void moveItem(int oldPos, int newPos) {

        BibleModel bm = data.get(oldPos);
        data.remove(oldPos);
        data.add(newPos, bm);
//        listData.remove(oldPos);
//        listData.add(newPos, item);
        new BibleUtil().reorderBible(books,oldPos, newPos);
        saveBookOrder();
        adapter.notifyItemMoved(oldPos, newPos);
    }
    private void deleteItem(final int position) {
        data.remove(position);
        adapter.notifyItemRemoved(position);
    }
    private void readBooksOrder(){
//        books.add("esv");
//        books.add("kjv");
//        books.add("niv");
//        books.add("nlt");

        readBookOrder();
    }

//    private void underlineButton(){
//        Button button = (Button) getActivity().findViewById(R.id.historyBtn);
//        if(button != null)
//            button.setPaintFlags(button.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//    }
    private void starbutton(){
//        imageButton = (ImageButton) getActivity().findViewById(R.id.favorate);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.getId
//            }
//        });
    }
    private void guideText(){
        TextView view = (TextView)getActivity().findViewById(R.id.guideMessage);
        if(view != null)
            view.setText(R.string.guideText);
    }
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
    private void removeMargin(Bundle savedInstanceState ){
        final FrameLayout frameLayout = (FrameLayout)getActivity().findViewById(R.id.biblecard);

        if ((frameLayout != null)){
//                && (savedInstanceState == null)) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) frameLayout.getLayoutParams();
            //left, top, right, bottom
            params.setMargins(0, 0, 0, 0);
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
        boolean loadingDone = isLoadingDone();
        if(checksame){
            if(previousQuery.equals(query) &&
                    !((CustomAdapter)adapter).getFirstItem().startsWith("Scripture taken from")){
                isSamequery = true;

            }

            new Util().printLog("******","1"+isSamequery);
            new Util().printLog("******",((CustomAdapter)adapter).getFirstItem());
        }else{
            loadingDone = true;
        }
        if(MainActivity.previousBottomButton == R.id.navigation_home){
            isSamequery = false;
            new Util().printLog("******","2"+isSamequery);
        }

        if(!isSamequery && result != null && loadingDone){
//                && result[0] !=null && result[1] != null && result[2] != null) {

//                    BibleModel bm = readJsonFile(result[0], result[1], result[2]);
            if(!new BibleModel().isValidBibleName(result[0])){
                Toast.makeText(this.getContext(), "Input a keyword. ex) Genesis 1 1",Toast.LENGTH_LONG).show();
                return;
            }
            data = ((CustomAdapter)adapter).removeAll(data);
            data = new BibleUtil().readBible(result[0], result[1], result[2], data, books);
//                    data.add(bm);

            String newQuery = result[0] + " " + result[1] + " " + result[2];
            previousQuery = newQuery;
            new Util().printLog("******", "new " + newQuery);

        }else{
            new Util().printLog("******","query same or nothing " + isLoadingDone());
//            Toast.makeText(this.getContext(), "Input a keyword. ex) John 1 1",Toast.LENGTH_LONG).show();
            return;
        }
    }

    public boolean isLoadingDone(){
        if(MainActivity.esvloadingdone && MainActivity.kjvloadingdone && MainActivity.nivloadingdone && MainActivity.nltloadingdone
         && MainActivity.esloadingdone && MainActivity.frloadingdone)
            return true;
        return false;
    }
    public void todayBible(){
        //        BibleModel bibleModel = new BibleUtil().readJsonFile("esv", BibleData.bibleNames[0],"1","1");
//        data.add(bibleModel);
    }
    public void nextBible(String pQuery){
        String query = pQuery;
        new Util().printLog("nextBible", query);
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
            new Util().printLog("***", " "+ index +","+ch + ","+ lastChapter);
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
        data = new BibleUtil().readBible(result[0], result[1], result[2], data, books);
//                    data.add(bm);

        String newQuery = result[0] + " " + result[1] + " " + result[2];
        previousQuery = newQuery;
        editsearch.setQuery(newQuery,true);

    }
    public void updateKeyword(String newQuery){
//        editsearch.requestFocus();
        editsearch.setQuery(newQuery,true);

    }
    public void preBible(String pQuery){
        String query = pQuery;
        new Util().printLog("nextBible", query);
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


            new Util().printLog("***", "chapter "+ chapter + " "  + index +","+ lastVersus + ","+ lastChapter );

            if(ch <=0 ){
                result[0] = BibleData.bibleNames[index-1];
                result[1] = ""+lastChapter;
                result[2] = ""+lastVersus;
            }else{
                result[1] = "" + ch;
                int prelastVerus = BibleData.verses[index][ch-1];
                result[2] = ""+ prelastVerus; //lastVersus;
                new Util().printLog("***", "chapter "+ chapter + " "  + index +","+ lastVersus + ","+ lastChapter + "," + prelastVerus);
            }

        }else{
            result[2] = newIndex.toString();
        }

        data = ((CustomAdapter)adapter).removeAll(data);
        data = new BibleUtil().readBible(result[0], result[1], result[2], data, books);
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
            new Util().printLog("***","onClick");
            if(v.getId() == R.id.favorate){
                new Util().printLog("***","star");
                String tag = (String)v.getTag();
                new Util().printLog("***",tag); // title

                if(MainActivity.starData.isStar(tag)){
                    changeStar(v,false);
                    MainActivity.starData.setStar(tag,false);
                }else{
                    changeStar(v,true);
                    MainActivity.starData.setStar(tag,true);
                }

//                Toast.makeText(context, "star",Toast.LENGTH_SHORT).show();
            }else if(v.getId() == R.id.noteBtn){
                String tag = (String)v.getTag();
                startNotes(tag);
            }else{
//                Toast.makeText(context, "else",Toast.LENGTH_SHORT).show();
            }
//            removeItem(v);
//            new Util().printLog("click", " " + v.getId());
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
        public void changeStar(View v, boolean clickbtn){

            ImageButton imageButton = (ImageButton)v;
            if(clickbtn){
                ((ImageButton) v).setImageResource(R.drawable.ic_star_gold_24dp);
            }else{
                ((ImageButton) v).setImageResource(R.drawable.ic_star_border_black_24dp);

            }

        }

        public void startNotes(String tag){
            Intent intent = new Intent(getActivity().getApplicationContext(), NotesActivity.class);
            intent.putExtra("title", tag);
//            startActivity(intent);
            activityResultLauncher.launch(intent);
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
//        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString(getString(R.string.previousBible),bible);
//        editor.apply();
        new Util().savePrefData(getString(R.string.previousBible),bible,getActivity());
    }
    public String readBibleLast(){
//        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
////        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
//        return sharedPref.getString(getString(R.string.previousBible),"Genesis 1 1");
        return new Util().readPrefData(getString(R.string.previousBible),"Genesis 1 1",getActivity());
    }
    public void saveBookOrder(){
        String value = "";
        for(int i=0; i<books.size();i++){
            value += books.get(i) + ",";
        }
        value = value.substring(0,value.length()-1);

        new Util().printLog("***************", value);
        new Util().savePrefData("sean.shin.app.books", value, getActivity());

    }
    public void readBookOrder(){
        String value = new Util().readPrefData("sean.shin.app.books",
                "esv,kjv,niv,nlt,rvr,ost",getActivity());

        String[] splitStr = value.split("\\s*,\\s*");
        for(int i=0; i<splitStr.length;i++){
            books.add(splitStr[i]);
        }
    }


    @Override
    public void onStart() {

        new Util().printLog("**","home:onStart" );

        if(previousQuery != null && startIndex == 0){
            previousQuery = readBibleLast();
            startIndex ++;
            updateKeyword(previousQuery);
        }

        if(adapter != null)
            adapter.notifyDataSetChanged();

//        MainActivity.previousQuery;
//        if(previousQuery.length() == 0){
//            String query = readBibleLast();
//            String keyword = query;
//            new Util().printLog("**read",query );
////            displayBible(query);
////            showLicense();
//            updateKeyword(query);
////            displayBible(keyword);
////            removeMargin(null);
////            startTimer();
//
//
//        }else{
//            updateKeyword(previousQuery);
//        }
        if(data.size() == 0){ //editsearch.getQuery().length() == 0){
            showLicense();
        }
//        else{
//            updateBible(editsearch.getQuery().toString());
//        }


//        underlineButton();
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onPause() {
        saveBibleRead(previousQuery);
        new Util().printLog("**save",previousQuery );
        MainActivity.previousQuery = previousQuery;
        super.onPause();
    }

    @Override
    public void onDestroy() {
        saveBibleRead(previousQuery);
        new Util().printLog("**save",previousQuery );
        MainActivity.previousQuery = previousQuery;
//        stoptimertask(null);
        super.onDestroy();
    }

//    public void startTimer() {
//        //set a new Timer
//        timer = new Timer();
//
//        //initialize the TimerTask's job
//        initializeTimerTask();
//
//        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
//        timer.schedule(timerTask, 5000, 10000); //
//
//        if(isLoadingDone())
//            stoptimertask(null);
//    }
//
//    public void stoptimertask(View v) {
//        //stop the timer, if it's not already null
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    }
//
//    public void initializeTimerTask() {
//
//        timerTask = new TimerTask() {
//            public void run() {
//
//                //use a handler to run a toast that shows the current timestamp
//                handler.post(new Runnable() {
//                    public void run() {
////                        String query = readBibleLast();
//                        updateKeyword(previousQuery);
//                    }
//                });
//            }
//        };
//    }

}


//
//<TextView
//                        android:id="@+id/howManyNotes"
//                                android:layout_width="wrap_content"
//                                android:layout_height="wrap_content"
//                                android:layout_gravity="right"
//                                android:paddingRight="8dp"
//
//                                android:text="0"/>
//
//<ImageButton
//                        android:id="@+id/noteBtn1"
//                                android:layout_width="wrap_content"
//                                android:layout_height="wrap_content"
//                                android:layout_gravity="right"
//                                android:background="@null"
//                                android:paddingRight="32dp"
//                                android:src="@drawable/ic_create_black_24dp" />
//