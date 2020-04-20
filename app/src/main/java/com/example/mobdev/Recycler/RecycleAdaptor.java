package com.example.mobdev.Recycler;

//Name - Richard Sharpe Marshall
//Matric Number - S1829121
import android.content.ClipData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobdev.Classes.set_items;
import com.example.mobdev.MainActivity;
import com.example.mobdev.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class RecycleAdaptor extends RecyclerView.Adapter<RecycleAdaptor.RecycleHolder> implements Filterable {

    //public Filter getFilter;
    private ArrayList<set_items> ArrayList1;
    private ArrayList<set_items> ArrayListCopy;

    public RecycleAdaptor(ArrayList<set_items> i_list){
        this.ArrayList1 = i_list;
        //ArrayListCopy = new ArrayList<>();
        ArrayListCopy = i_list;
        notifyDataSetChanged();

    }


/*
    public Filter itemFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            SimpleDateFormat format = new SimpleDateFormat("ddd, dd MMM");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            //Calendar currentDate = Calendar.getInstance();


            ArrayList<set_items> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() ==0){
                filteredList.addAll(ArrayListCopy);
            }
            else{
                //String filterPattern = constraint.toString();
                for(set_items item :ArrayList1){
                    if(constraint.equals(item.getPublishDate())){filteredList.add(item);}
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            ArrayListCopy.clear();
            ArrayListCopy.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


 */
private Filter itemFilter = new Filter() {
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        SimpleDateFormat format = new SimpleDateFormat("ddd, dd MMM");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar currentDate = Calendar.getInstance();

        ArrayList<set_items> filteredList = new ArrayList<>();
        if(constraint == null || constraint.length() ==0){
            filteredList.addAll(ArrayListCopy);
        }else{

            for(set_items item : ArrayListCopy){
                if(item.getfDate().equals(constraint)){filteredList.add(item);}
            }
        }

        FilterResults results = new FilterResults();
        results.values = filteredList;

        notifyDataSetChanged();

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        ArrayListCopy.clear();
        ArrayListCopy.addAll((ArrayList)results.values);
        notifyDataSetChanged();

    }
};
    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    @NonNull
    @Override
    public RecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler, parent, false);
        RecycleHolder irh = new RecycleHolder(v);
        return irh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleHolder holder, int position) {

        set_items currentItem = ArrayList1.get(position);
        holder.title.setText(currentItem.getTitle());
        holder.desc.setText(currentItem.getDescription());
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        holder.pDate.setText(currentItem.getPublishDate());

    }

    @Override
    public int getItemCount() { return ArrayList1.size();}






    public class RecycleHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView desc;
        public TextView pDate;

        public RecycleHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            pDate = itemView.findViewById(R.id.pDate);

        }
    }
}
