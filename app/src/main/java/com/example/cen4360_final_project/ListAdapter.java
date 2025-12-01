package com.example.cen4360_final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//Adapter for displaying grocery lists in the RecyclerView
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<GroceryListEntity> lists = new ArrayList<>();
    private OnListClickListener listener;

    //Listener interface for click events
    public interface OnListClickListener {
        void onListClick(GroceryListEntity list);
    }

    //Constructor takes listener from MainActivity
    public ListAdapter(OnListClickListener listener) {
        this.listener = listener;
    }

    //Update adapter data
    public void setData(List<GroceryListEntity> newLists) {
        lists = newLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list, parent, false);
        return new ListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        GroceryListEntity list = lists.get(position);
        holder.textViewListName.setText(list.listName);

        //Handle clicking on a list
        holder.itemView.setOnClickListener(v -> listener.onListClick(list));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    //ViewHolder maps row_list.xml views to Java objects
    static class ListViewHolder extends RecyclerView.ViewHolder {

        TextView textViewListName;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewListName = itemView.findViewById(R.id.textViewListName);
        }
    }
}
