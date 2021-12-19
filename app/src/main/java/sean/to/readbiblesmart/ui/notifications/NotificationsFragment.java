package sean.to.readbiblesmart.ui.notifications;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
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
import sean.to.readbiblesmart.util.Util;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private View root;
//    private String title;

    int[][] closeIconStates = {
            { android.R.attr.state_pressed },
            { android.R.attr.state_focused, android.R.attr.state_hovered },
            { android.R.attr.state_focused },
            { android.R.attr.state_hovered },
            { android.R.attr.state_enabled }, {} };



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
//        final TextView textlabelView = root.findViewById(R.id.notelabel);
        Button showNotes = root.findViewById(R.id.addNotes);
        this.root = root;

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

        hideNotes(showNotes);

        return root;
    }
    public void showFavorite(final View view, TextView textView){
        final ChipGroup chipGroup = view.findViewById(R.id.tag_group);
//        final TextView textlabelView = view.findViewById(R.id.notelabel);
        final Button showNotes = view.findViewById(R.id.addNotes);
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
                closeChip(chip);
                chips++;
                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = v.getTag().toString();
                        String body = new BibleUtil().readVerse(title);
                        TextView textView = view.findViewById(R.id.text_notifications);
                        textView.setText(body);
                        showNotes(showNotes);
                        int notes = MainActivity.notesData.hasHowmanynotes(title);
                        showNotes.setText(MainActivity.notesData.expressionNotes(notes));
//                        Button showNotes = view.findViewById(R.id.addNotes);
                        showNotes.setTag(title);

                    }
                });
            }
          if(!chip.getText().equals(R.string.app_name))
            chipGroup.addView(chip);

        }

//        ImageButton showNotes = view.findViewById(R.id.addNotes);

        if(chips == 0){
            chipGroup.setVisibility(View.GONE);
            showNotes.setVisibility(View.GONE);
//            textlabelView.setVisibility(View.GONE);
            textView.setText("No favorites yet. Please press star buttons when you find good verses.");
            hideNotes(showNotes);
        }else {
            chipGroup.setVisibility(View.VISIBLE);
            showNotes.setVisibility(View.VISIBLE);
//            textlabelView.setVisibility(View.VISIBLE);
            showNotes(showNotes);
        }
//        hideNotes(textlabelView,showNotes);
    }
    private void callNotesActivity(String title){
        if(title == null){
            new Util().toastMessage(getContext(),"Not supported");
            return;
        }
        Intent intent = new Intent(getActivity().getApplicationContext(), NotesActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }
    private void hideNotes(Button view){
//        text.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
    }
    private void showNotes(Button view){
//        text.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        checkChipStatus();

    }
    private void checkChipStatus(){
        final ChipGroup chipGroup = root.findViewById(R.id.tag_group);

//        Iterator keys = MainActivity.starData.starList.keys();
//        int chips = 0;
//        while(keys.hasNext()) {
//            String dynamicKey = (String) keys.next();
//
//            if (!MainActivity.starData.isStar(dynamicKey)) {
//               chipGroup.get
//            }
//            chips++;
//        }

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = ((Chip) chipGroup.getChildAt(i));
            String title = chip.getText().toString();
            if (!MainActivity.starData.isStar(title)){
                chipGroup.removeView(chip);
            }
        }
    }
    private void closeChip(final Chip chip){
        chip.setCloseIconVisible(true);

        int fgColor = chip.getCurrentTextColor();

        int[] closeIconColors = {
                ColorUtils.setAlphaComponent(fgColor, 0xff),
                ColorUtils.setAlphaComponent(fgColor, 0xff),
                ColorUtils.setAlphaComponent(fgColor, 0xde),
                ColorUtils.setAlphaComponent(fgColor, 0xb8),
                ColorUtils.setAlphaComponent(fgColor, 0x8a),
                ColorUtils.setAlphaComponent(fgColor, 0x36) };

        ColorStateList closeIconTint = new ColorStateList(closeIconStates, closeIconColors);
        chip.setCloseIconTint(closeIconTint);

        final ChipGroup chipGroup = root.findViewById(R.id.tag_group);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = chip.getTag().toString();
                chipGroup.removeView(chip);
                MainActivity.starData.setStar(title, false);
            }
        });
    }
    //    public String readVerse(String query){
//        String book = "esv";
//        String title = query;
//        int index = 6;
//        if(query.startsWith("[ESV]")){
//            query = query.substring(index);
//            new Util().printLog("=========",query);
//        }
//        if(query.startsWith("[KJV]")){
//            query = query.substring(index);
//            book = "kjv";
//            new Util().printLog("=========",query);
//        }
//        if(query.startsWith("[NIV]")){
//            query = query.substring(index);
//            book = "niv";
//            new Util().printLog("=========",query);
//        }
//        if(query.startsWith("[NLT]")){
//            query = query.substring(index);
//            book = "nlt";
//            new Util().printLog("=========",query);
//        }
//        String[] result = new BibleUtil().parseQuery(query);
//        String body = new BibleUtil().readBible(book,result[0], result[1], result[2]);
//
//        return title + "\n\n"+ body;
//    }
}

//
//<TextView
//            android:id="@+id/notelabel"
//                    android:layout_width="wrap_content"
//                    android:layout_height="wrap_content"
//                    android:layout_gravity="left"
//                    android:layout_marginStart="8dp"
//                    android:layout_marginTop="8dp"
//                    android:layout_marginRight="8dp"
//                    android:layout_marginBottom="8dp"
//                    android:textColor="@color/colorBlue"
//
//                    android:text="0 notes" />
//<ImageButton
//            android:id="@+id/addNotes1"
//                    android:layout_width="40dp"
//                    android:layout_height="40dp"
//                    android:layout_alignParentRight="true"
//                    android:background="@null"
//                    android:src="@drawable/ic_create_black_24dp" />