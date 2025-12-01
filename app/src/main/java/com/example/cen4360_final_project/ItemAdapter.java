package com.example.cen4360_final_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Adapter for displaying grocery items in a list
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<GroceryItemEntity> items = new ArrayList<>();
    private OnItemClickListener listener;

    // Listener for tapping items to edit
    public interface OnItemClickListener {
        void onItemClick(GroceryItemEntity item);
    }

    public ItemAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Update adapter data
    public void setData(List<GroceryItemEntity> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        GroceryItemEntity item = items.get(position);

        holder.textName.setText(item.itemName);
        holder.textDetails.setText(item.quantity + " | " + item.category);
        holder.checkBox.setChecked(item.isChecked);

        // Clicking the row opens EditItemActivity
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));

        // Toggling checkbox updates the database instantly
        holder.checkBox.setOnClickListener(v -> {
            item.isChecked = holder.checkBox.isChecked();
            AppDatabase db = AppDatabase.getInstance(holder.itemView.getContext());
            db.itemDao().updateItem(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Maps row_item.xml to Java objects
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView textName, textDetails;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBoxItem);
            textName = itemView.findViewById(R.id.textViewItemName);
            textDetails = itemView.findViewById(R.id.textViewItemDetails);
        }
    }
}
