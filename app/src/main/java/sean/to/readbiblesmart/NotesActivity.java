package sean.to.readbiblesmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sean.to.readbiblesmart.data.NoteModel;
import sean.to.readbiblesmart.ui.home.HomeFragment;
import sean.to.readbiblesmart.util.BibleUtil;
import sean.to.readbiblesmart.util.Util;

public class NotesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<NoteModel> data;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        data = new ArrayList<NoteModel>();

        Intent intent = getIntent();

        if(intent != null){
            title = intent.getStringExtra("title");

            String body = new BibleUtil().readBody(title);
            TextView textTitle = findViewById(R.id.textTitle);
            TextView textBody = findViewById(R.id.textBody);
            textTitle.setText(title);
            textBody.setText(body);


            data = readNotes(title,data);

            if(data == null)
                data = new ArrayList<NoteModel>();
            showHowmanycomments(data.size());
        }

        recyclerView = (RecyclerView) findViewById(R.id.notes);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);



        adapter = new NotesAdapter(data);
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final TextView comment = findViewById(R.id.typecommet);
        ImageButton sendButton = findViewById(R.id.sendcomment);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendButton.setTooltipText("Save");
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String note = comment.getText().toString();
                new Util().printLog("---click---", note + "," + title );
                if(note.length() > 0){
                    NoteModel model = new NoteModel(note, new Util().getToday(),Integer.toString(data.size()+1));
                    data.add(0, model);
                    MainActivity.notesData.putNotes(title, note);
//                    MainActivity.notesData.notelist = MainActivity.notesData.putNotes(title, note, MainActivity.notesData.notelist);
                    new Util().printLog("------", ""+data.size() );
                    adapter.notifyDataSetChanged();
                    showHowmanycomments(data.size());
                    comment.setText("");
                }
            }
        });


        cardDeco(title);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }
    public void showHowmanycomments(int comments){
        TextView view = findViewById(R.id.label);
        if(comments == 1)
            view.setText("" + comments + " note");
        else
            view.setText("" + comments + " notes");
    }
    public void cardDeco(String title){
        CardView view = findViewById(R.id.bible);

        if(title == null) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.esv));
            return;
        }

        if(title.startsWith("[ESV]")) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.esv));
        }
        if(title.startsWith("[KJV]")) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.kjv));
        }
        if(title.startsWith("[NIV]")) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.niv));
        }
        if(title.startsWith("[NLT]")) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.nlt));
        }

        if(title.startsWith("[RVR]")) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.es));
        }
        if(title.startsWith("[OST]")) {
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.fr));
        }
    }
    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    //                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
//                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return true;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }
    private void deleteItem(final int position) {
        data.remove(position);
        adapter.notifyItemRemoved(position);

        showHowmanycomments(data.size());
        MainActivity.notesData.delNotes(title, position);
    }
    public ArrayList<NoteModel> readNotes(String tag, ArrayList<NoteModel> data){
        JSONObject result = MainActivity.notesData.getNotes(tag);
        new Util().printLog("---read---", MainActivity.notesData.notelist.toString());
        if(result != null){
            try {
                int total = result.getInt("total");
                new Util().printLog("------", tag + "," + total );
                for(int i=total; i > 0; i--){

//                    String body = result.getString(""+i);
                    JSONObject rObject = (JSONObject)result.get(Integer.toString(i));
                    String body = rObject.getString("body");
                    String date = rObject.getString("date");
//                    String id = rObject.getString("id");
//                    String date = "";
//                    String id = ""+i;
                    NoteModel model = new NoteModel(body,date,Integer.toString(i));
                    data.add(model);

                }
                return data;
            }catch (JSONException e){
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeWithResult();

    }

    private void closeWithResult(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", title);
        setResult(RESULT_OK, resultIntent);

        finish();
    }
    public void postBibleNames(String title){

        FragmentManager ft = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction =ft.beginTransaction();
//        HomeFragment frag = (HomeFragment)ft.findFragmentById(R.id.navigation_home);

//        if(MainActivity.homeFragment == null){
//            MainActivity.homeFragment = new HomeFragment();
//            fragmentTransaction.replace(R.id.container, MainActivity.homeFragment,"home");
//            //        fragmentTransaction.add(R.id.navigation_dashboard, frag);
//            fragmentTransaction.setReorderingAllowed(true);
//            fragmentTransaction.addToBackStack("home");
//            // ft.addToBackStack(null);
//            fragmentTransaction.commit();
//        }

//        ft.popBackStack();
//
//        Bundle result = new Bundle();
//        result.putString("bible", title);
//        ft.setFragmentResult("requestKey", result);


//        selectItem(R.id.navigation_home);

    }


    @Override
    protected void onDestroy() {
        MainActivity.notesData.saveNoteData(this);
        closeWithResult();
//        postBibleNames(title);
        super.onDestroy();
    }
    //    https://www.geeksforgeeks.org/implement-comment-on-a-particular-blog-functionality-in-social-media-android-app/implement-comment-on-a-particular-blog-functionality-in-social-media-android-app
}
