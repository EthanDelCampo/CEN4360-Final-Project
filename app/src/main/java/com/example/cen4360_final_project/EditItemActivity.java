package com.example.cen4360_final_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

// Add or edit a grocery item
public class EditItemActivity extends AppCompatActivity {

    private AppDatabase db;

    private EditText editTextName, editTextQuantity, editTextCategory;
    private Button buttonSave, buttonCancel, buttonDelete;

    private int itemId = -1;   // -1 = new item
    private int parentListId;  // always required

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        db = AppDatabase.getInstance(this);

        // Link XML views
        editTextName = findViewById(R.id.editTextItemName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextCategory = findViewById(R.id.editTextCategory);

        buttonSave = findViewById(R.id.buttonSaveItem);
        buttonCancel = findViewById(R.id.buttonCancelItem);
        buttonDelete = findViewById(R.id.buttonDeleteItem);

        // Always passed in — tells us which list this item belongs to
        parentListId = getIntent().getIntExtra("listId", -1);

        // Check if editing existing item
        if (getIntent().hasExtra("itemId")) {
            itemId = getIntent().getIntExtra("itemId", -1);
            loadExistingItem();
        } else {
            // New item → disable delete button
            buttonDelete.setEnabled(false);
            buttonDelete.setAlpha(0.3f);
        }

        // Buttons
        buttonSave.setOnClickListener(v -> saveItem());
        buttonCancel.setOnClickListener(v -> finish());
        buttonDelete.setOnClickListener(v -> deleteItem());
    }

    // Load item details from database
    private void loadExistingItem() {
        GroceryItemEntity item = null;

        // Get all items for the list and find our item
        for (GroceryItemEntity i : db.itemDao().getItemsForList(parentListId)) {
            if (i.itemId == itemId) {
                item = i;
                break;
            }
        }

        if (item != null) {
            editTextName.setText(item.itemName);
            editTextQuantity.setText(String.valueOf(item.quantity));
            editTextCategory.setText(item.category);
        }
    }

    // Save new or updated item
    private void saveItem() {
        String name = editTextName.getText().toString().trim();
        String qtyString = editTextQuantity.getText().toString().trim();
        String category = editTextCategory.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Item name cannot be empty");
            return;
        }

        int quantity = qtyString.isEmpty() ? 1 : Integer.parseInt(qtyString);

        if (itemId == -1) {
            // Create a new item
            GroceryItemEntity item = new GroceryItemEntity(
                    parentListId,
                    name,
                    quantity,
                    category,
                    false
            );
            db.itemDao().insertItem(item);
        } else {
            // Update existing item
            GroceryItemEntity item = new GroceryItemEntity(
                    parentListId,
                    name,
                    quantity,
                    category,
                    false
            );
            item.itemId = itemId;
            db.itemDao().updateItem(item);
        }

        finish();
    }

    // Delete an existing item
    private void deleteItem() {
        if (itemId != -1) {
            GroceryItemEntity item = new GroceryItemEntity(parentListId, "", 0, "", false);
            item.itemId = itemId;
            db.itemDao().deleteItem(item);
        }
        finish();
    }
}
