package sean.to.readbiblesmart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sean.to.readbiblesmart.data.NoteModel;
import sean.to.readbiblesmart.util.Util;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder>  {
    private ArrayList<NoteModel> dataSet;

    public NotesAdapter(ArrayList<NoteModel> data){
        this.dataSet = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView, dateView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.notesitem);
            dateView = itemView.findViewById(R.id.date);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notes_items, parent, false);

        new Util().printLog("N:onCreateViewHolder",view == null ? "null" : "valid");

//        view.setOnClickListener(HomeFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView textView = holder.textView;
        TextView dateView = holder.dateView;
        if(dataSet.size() == 0) return;
        if(dataSet.get(position) == null) return;


        String body = dataSet.get(position).getBody();
        String date = dataSet.get(position).getDate();

        new Util().printLog("N:onBindViewHolder", body );
        new Util().printLog("N:onBindViewHolder", body == null ? "null" : "valid");

        textView.setText(body == null ? "no data" : body);
        dateView.setText(date == null ? "no data" : date);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void removeAt(int position, ArrayList<NoteModel> data) {
        data.remove(position);

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }
}
