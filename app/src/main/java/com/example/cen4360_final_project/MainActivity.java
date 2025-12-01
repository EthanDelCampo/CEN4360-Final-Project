package com.example.cen4360_final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//AI was used to assist with debugging

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;                       //Database instance
    private List<GroceryListEntity> listData;     //All grocery lists
    private ListAdapter listAdapter;              //RecyclerView adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get database
        db = AppDatabase.getInstance(this);

        //Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Adapter with click listener
        listAdapter = new ListAdapter(list -> {
            //Open the items screen for the selected list
            Intent intent = new Intent(MainActivity.this, ItemsActivity.class);
            intent.putExtra("listId", list.listId);
            intent.putExtra("listName", list.listName);
            startActivity(intent);
        });

        recyclerView.setAdapter(listAdapter);

        //Add new list button
        FloatingActionButton fab = findViewById(R.id.fabAddList);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditListActivity.class);
            startActivity(intent);
        });
    }

    //Reload lists every time activity returns to foreground
    @Override
    protected void onResume() {
        super.onResume();
        loadLists();
    }

    //Load all lists from the database
    private void loadLists() {
        listData = db.listDao().getAllLists();
        listAdapter.setData(listData);
    }
}
