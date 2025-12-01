package com.example.cen4360_final_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

// Add or edit a grocery list
public class EditListActivity extends AppCompatActivity {

    private AppDatabase db;
    private EditText editTextListName;

    private int listId = -1;   // -1 = creating new list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);

        db = AppDatabase.getInstance(this);

        editTextListName = findViewById(R.id.editTextListName);
        Button buttonSave = findViewById(R.id.buttonSaveList);
        Button buttonCancel = findViewById(R.id.buttonCancelList);
        Button buttonDelete = findViewById(R.id.buttonDeleteList);

        // Check if editing an existing list
        if (getIntent().hasExtra("listId")) {
            listId = getIntent().getIntExtra("listId", -1);
            String listName = getIntent().getStringExtra("listName");
            editTextListName.setText(listName);
        } else {
            // New list → hide delete button
            buttonDelete.setEnabled(false);
            buttonDelete.setAlpha(0.3f);
        }

        // Save button
        buttonSave.setOnClickListener(v -> saveList());

        // Cancel button
        buttonCancel.setOnClickListener(v -> finish());

        // Delete list
        buttonDelete.setOnClickListener(v -> deleteList());
    }

    // Save new or updated list
    private void saveList() {
        String name = editTextListName.getText().toString().trim();
        if (name.isEmpty()) {
            editTextListName.setError("List name cannot be empty");
            return;
        }

        if (listId == -1) {
            // Create new list
            GroceryListEntity newList = new GroceryListEntity(name);
            db.listDao().insertList(newList);
        } else {
            // Update existing list
            GroceryListEntity list = new GroceryListEntity(name);
            list.listId = listId;
            db.listDao().updateList(list);
        }

        finish();
    }

    // Delete the selected list
    private void deleteList() {
        if (listId != -1) {
            GroceryListEntity list = new GroceryListEntity("");
            list.listId = listId;
            db.listDao().deleteList(list);
        }
        finish();
    }
}
