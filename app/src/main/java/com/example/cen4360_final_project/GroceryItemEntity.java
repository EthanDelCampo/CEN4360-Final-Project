package com.example.cen4360_final_project;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grocery_items")
public class GroceryItemEntity {

    @PrimaryKey(autoGenerate = true)
    public int itemId;

    public int parentListId;  // Foreign Key → listId

    public String itemName;
    public int quantity;
    public String category;

    public boolean isChecked;

    public GroceryItemEntity(int parentListId, String itemName, int quantity, String category, boolean isChecked) {
        this.parentListId = parentListId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.category = category;
        this.isChecked = isChecked;
    }
}
