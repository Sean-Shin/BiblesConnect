package sean.to.readbiblesmart;

import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sean.to.readbiblesmart.data.BibleModel;
import sean.to.readbiblesmart.ui.home.HomeFragment;
import sean.to.readbiblesmart.util.Util;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<BibleModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        TextView textHowmanynotes;
        CardView parentView;
        ImageButton imageButton;
        Button notesButton;
//        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.parentView = (CardView)itemView.findViewById(R.id.card_view);
            this.imageButton = (ImageButton)itemView.findViewById(R.id.favorate);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.imageButton.setTooltipText("Favorite");
            }

            this.notesButton = itemView.findViewById(R.id.noteBtn);

//            this.textHowmanynotes = (TextView)itemView.findViewById(R.id.howManyNotes);

//            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            new Util().printLog("adapter2", this.textViewName.toString());


//            imageButton.setOnClickListener(
//                    HomeFragment.myOnClickListener;
//            );

//            itemView.findViewById(R.id.prebtn).setOnClickListener(
//                    HomeFragment.myOnClickListener
//
//            );
//            itemView.findViewById(R.id.nextbtn).setOnClickListener(
//                    HomeFragment.myOnClickListener
//            );

//
//            mButton.setOnClickListener(
//                    new View.OnClickListener()
//                    {
//                        public void onClick(View view)
//                        {
//                            Log.v("EditText", mEdit.getText().toString());
//                        }
//                    });


        }


    }


    public CustomAdapter(ArrayList<BibleModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        new Util().printLog("onCreateViewHolder",view == null ? "null" : "valid");

        view.setOnClickListener(HomeFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageButton imageButton = holder.imageButton;
        Button notesButton = holder.notesButton;
        TextView textHowmanynotes = holder.textHowmanynotes;

//        ImageView imageView = holder.imageViewIcon;

//        String name = dataSet.get(listPosition).getName();
//        String version = dataSet.get(listPosition).getVersion();
        if(dataSet.get(listPosition) == null) return;


        String name = dataSet.get(listPosition).getTitle();
        String version = dataSet.get(listPosition).getBody();

        new Util().printLog("onBindViewHolder", name +"," +version);
        new Util().printLog("onBindViewHolder", textViewName == null ? "null" : "valid");

        textViewName.setText(name == null ? "no data" : name);
        textViewVersion.setText(version == null ? "no data" : version);
//        imageView.setImageResource(dataSet.get(listPosition).getImage());
        updateCardColor(holder.parentView,name,imageButton,notesButton);
        updateStarButton(name,imageButton);
        imageButton.setOnClickListener(HomeFragment.myOnClickListener);
        notesButton.setOnClickListener(HomeFragment.myOnClickListener);
//        showHowmanynotes(textHowmanynotes, name);
        showHowmanynotes(notesButton, name);

        if(version.startsWith("Scripture taken from")){
            hideNotes(textHowmanynotes, notesButton);
            imageButton.setVisibility(View.GONE);
        }else{
            showNotes(textHowmanynotes, notesButton);
            imageButton.setVisibility(View.VISIBLE);
        }

    }
    private void showHowmanynotes(Button view, String title){
        int notes = MainActivity.notesData.hasHowmanynotes(title);
        view.setText(MainActivity.notesData.expressionNotes(notes));
//        if(notes == 0)
//            view.setText("New Note");
//        else{
//            view.setText("" + notes + " notes");
//        }
    }
    private void updateStarButton(String name, ImageButton imageButton){
        if(MainActivity.starData.isStar(name))
            imageButton.setImageResource(R.drawable.ic_star_gold_24dp);
        else
            imageButton.setImageResource(R.drawable.ic_star_border_black_24dp);
    }
    private void updateCardColor(CardView view, String name, ImageButton imageButton, Button notesButton){
        if(name.startsWith("[ESV]")){
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.esv));
            view.setTag("esv");
            imageButton.setTag(name);
            notesButton.setTag(name);
//            imageButton.setTag("esv");
            setEffect(view);
            hideCard(view, "esv");
        }
        else if(name.startsWith("[KJV]")){
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kjv));
            view.setTag("kjv");
            imageButton.setTag(name);
            notesButton.setTag(name);
//            imageButton.setTag("kjv");
            setEffect(view);
            hideCard(view, "kjv");
        }
        else if(name.startsWith("[NIV]")){
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.niv));
            view.setTag("niv");
            imageButton.setTag(name);
            notesButton.setTag(name);
//            imageButton.setTag("niv");
            setEffect(view);
            hideCard(view, "niv");
        }
        else if(name.startsWith("[NLT]")){
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.nlt));
            view.setTag("nlt");
            imageButton.setTag(name);
            notesButton.setTag(name);
//            imageButton.setTag("nlt");
            setEffect(view);
            hideCard(view, "nlt");
        }
        else if(name.startsWith("[RVR]")){ //es
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.es));
            view.setTag("rvr");
            imageButton.setTag(name);
            notesButton.setTag(name);
//            imageButton.setTag("nlt");
            setEffect(view);
            hideCard(view, "rvr");
        }
        else if(name.startsWith("[OST]")){ //fr
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.fr));
            view.setTag("ost");
            imageButton.setTag(name);
            notesButton.setTag(name);
//            imageButton.setTag("nlt");
            setEffect(view);
            hideCard(view, "ost");
        }

    }
    private void setEffect(CardView view){
//        view.setCardElevation(20f);
//        view.setRadius(20f);

//        view.setContentPadding(10,10,10,10);
//        view.setPreventCornerOverlap(true);

    }
    private void hideCard(CardView view, String book){
        if(!MainActivity.settingsData.isSettings(book))
            view.setVisibility(View.GONE);
        else{
            if(!view.isShown())
                view.setVisibility(View.VISIBLE);
        }

    }
    public void hideNotes(TextView text, Button view){
//        text.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
    }
    public void showNotes(TextView text, Button view){
//        text.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public String getFirstItem(){
        if(dataSet.size() > 0)
        return dataSet.get(0) != null ? dataSet.get(0).getBody() : "";
        else return "";
    }

    public void removeAt(int position, ArrayList<BibleModel> data) {
        data.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }
    public void update(){
        notifyDataSetChanged();
    }
    public ArrayList<BibleModel> removeAll(ArrayList<BibleModel> data){
        int size = data.size();
        for(int i= size -1;i >=0;i--){
           removeAt(i, data);
        }
        return data;
//        data.removeAll(data);
    }

}