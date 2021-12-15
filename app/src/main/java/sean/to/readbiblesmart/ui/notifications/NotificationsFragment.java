package sean.to.readbiblesmart.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONObject;

import java.util.Iterator;

import sean.to.readbiblesmart.MainActivity;
import sean.to.readbiblesmart.NotesActivity;
import sean.to.readbiblesmart.R;
import sean.to.readbiblesmart.util.BibleUtil;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
//    private String title;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        ImageButton showNotes = root.findViewById(R.id.addNotes);

        showFavorite(root, textView);

//        textView.setText(getResources().getString(R.string.myself)
//        + "\n\n" + getResources().getString(R.string.license_overview)
//        + "\n\n" + getResources().getString(R.string.license));
//        notificationsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        textView.setMovementMethod(new ScrollingMovementMethod());

        showNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag() != null){
                    String title = v.getTag().toString();
                    callNotesActivity(title);
                }else{

                }
            }
        });

        return root;
    }
    public void showFavorite(final View view, TextView textView){
        final ChipGroup chipGroup = view.findViewById(R.id.tag_group);
        final TextView textlabelView = view.findViewById(R.id.notelabel);
//        JSONObject object = new JSONObject(json);
//        Iterator keys = object.keys();
//        while(keys.hasNext()) {
//            String dynamicKey = (String)keys.next();
//            JSONObject line = object.getJSONObject(dynamicKey);
//            String desc = line.getString("desc");
//        }

//        for(String key : jsonObject.keySet())
        Iterator keys = MainActivity.starData.starList.keys();
        int chips = 0;
        while(keys.hasNext()) {
            String dynamicKey = (String)keys.next();
//            Chip chip = new Chip(view.getContext());
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.chip_view, chipGroup, false);


           if(MainActivity.starData.isStar(dynamicKey)){

                chip.setText(dynamicKey);
                chip.setTag(dynamicKey);
                chips++;
                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = v.getTag().toString();
                        String body = new BibleUtil().readVerse(title);
                        TextView textView = view.findViewById(R.id.text_notifications);
                        textView.setText(body);
                        int notes = MainActivity.notesData.hasHowmanynotes(title);
                        textlabelView.setText(MainActivity.notesData.expressionNotes(notes));
                        ImageButton showNotes = view.findViewById(R.id.addNotes);
                        showNotes.setTag(title);

                    }
                });
            }
          if(!chip.getText().equals(R.string.app_name))
            chipGroup.addView(chip);

        }

        ImageButton showNotes = view.findViewById(R.id.addNotes);

        if(chips == 0){
            chipGroup.setVisibility(View.GONE);
            showNotes.setVisibility(View.GONE);
            textlabelView.setVisibility(View.GONE);
            textView.setText("No favorites yet. Please press star buttons when you find good verses.");
        }else {
            chipGroup.setVisibility(View.VISIBLE);
            showNotes.setVisibility(View.VISIBLE);
            textlabelView.setVisibility(View.VISIBLE);
        }
        hideNotes(textlabelView,showNotes);
    }
    private void callNotesActivity(String title){
        Intent intent = new Intent(getActivity().getApplicationContext(), NotesActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }
    private void hideNotes(TextView text, ImageButton view){
        text.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
    }
//    public String readVerse(String query){
//        String book = "esv";
//        String title = query;
//        int index = 6;
//        if(query.startsWith("[ESV]")){
//            query = query.substring(index);
//            Log.d("=========",query);
//        }
//        if(query.startsWith("[KJV]")){
//            query = query.substring(index);
//            book = "kjv";
//            Log.d("=========",query);
//        }
//        if(query.startsWith("[NIV]")){
//            query = query.substring(index);
//            book = "niv";
//            Log.d("=========",query);
//        }
//        if(query.startsWith("[NLT]")){
//            query = query.substring(index);
//            book = "nlt";
//            Log.d("=========",query);
//        }
//        String[] result = new BibleUtil().parseQuery(query);
//        String body = new BibleUtil().readBible(book,result[0], result[1], result[2]);
//
//        return title + "\n\n"+ body;
//    }
}