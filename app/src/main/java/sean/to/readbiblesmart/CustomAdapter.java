package sean.to.readbiblesmart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sean.to.readbiblesmart.data.BibleModel;
import sean.to.readbiblesmart.ui.home.HomeFragment;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<BibleModel> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        CardView parentView;
//        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.parentView = (CardView)itemView.findViewById(R.id.card_view);

//            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            Log.d("adapter2", this.textViewName.toString());

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

        Log.d("onCreateViewHolder",view == null ? "null" : "valid");

        view.setOnClickListener(HomeFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
//        ImageView imageView = holder.imageViewIcon;

//        String name = dataSet.get(listPosition).getName();
//        String version = dataSet.get(listPosition).getVersion();
        if(dataSet.get(listPosition) == null) return;

        String name = dataSet.get(listPosition).getTitle();
        String version = dataSet.get(listPosition).getBody();

        Log.d("onBindViewHolder", name +"," +version);
        Log.d("onBindViewHolder", textViewName == null ? "null" : "valid");

        textViewName.setText(name == null ? "no data" : name);
        textViewVersion.setText(version == null ? "no data" : version);
//        imageView.setImageResource(dataSet.get(listPosition).getImage());
        updateCardColor(holder.parentView,name);
    }
    private void updateCardColor(CardView view, String name){
        if(name.startsWith("[ESV]")){
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.esv));
            setEffect(view);
        }
        if(name.startsWith("[KJV]")){
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.kjv));
            setEffect(view);
        }
        if(name.startsWith("[NIV]")){
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.niv));
            setEffect(view);
        }
        if(name.startsWith("[NLT]")){
            setEffect(view);
            view.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.nlt));
        }

    }
    private void setEffect(CardView view){
//        view.setCardElevation(20f);
//        view.setRadius(20f);

//        view.setContentPadding(10,10,10,10);
//        view.setPreventCornerOverlap(true);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void removeAt(int position, ArrayList<BibleModel> data) {
        data.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
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