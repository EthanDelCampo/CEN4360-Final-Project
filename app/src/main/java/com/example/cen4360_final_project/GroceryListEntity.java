package com.example.cen4360_final_project;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "grocery_lists")
public class GroceryListEntity {

    @PrimaryKey(autoGenerate = true)
    public int listId;

    public String listName;

    public GroceryListEntity(String listName) {
        this.listName = listName;
    }
}
