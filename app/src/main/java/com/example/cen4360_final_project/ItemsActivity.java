package com.example.cen4360_final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Shows all items inside a selected grocery list
public class ItemsActivity extends AppCompatActivity {

    private AppDatabase db;

    private int listId;
    private String listName;

    private List<GroceryItemEntity> itemData;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        // Extract the selected list info from the intent
        listId = getIntent().getIntExtra("listId", -1);
        listName = getIntent().getStringExtra("listName");

        // Set list title at the top of the screen
        TextView title = findViewById(R.id.textViewListTitle);
        title.setText(listName);

        db = AppDatabase.getInstance(this);

        // Set up RecyclerView for items
        RecyclerView recyclerView = findViewById(R.id.recyclerViewItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adapter — handles clicking items to edit them
        itemAdapter = new ItemAdapter(item -> {
            Intent intent = new Intent(ItemsActivity.this, EditItemActivity.class);
            intent.putExtra("itemId", item.itemId);
            intent.putExtra("listId", listId);
            startActivity(intent);
        });

        recyclerView.setAdapter(itemAdapter);

        // Add Item button
        Button addItemButton = findViewById(R.id.buttonAddItem);
        addItemButton.setOnClickListener(v -> {
            Intent intent = new Intent(ItemsActivity.this, EditItemActivity.class);
            intent.putExtra("listId", listId);
            startActivity(intent);
        });

        // Back button
        Button backButton = findViewById(R.id.buttonBackToLists);
        backButton.setOnClickListener(v -> finish());
    }


    // Reload items when returning from edit screens
    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
    }

    private void loadItems() {
        itemData = db.itemDao().getItemsForList(listId);
        itemAdapter.setData(itemData);
    }
}
