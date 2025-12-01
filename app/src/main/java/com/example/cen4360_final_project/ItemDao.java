package com.example.cen4360_final_project;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface ItemDao {

    //Inserts new item
    @Insert
    long insertItem(GroceryItemEntity item);

    //Gets all items for a particular list
    @Query("SELECT * FROM grocery_items WHERE parentListId = :listId ORDER BY itemName ASC")
    List<GroceryItemEntity> getItemsForList(int listId);

    //Updates item (name, quantity, category, isChecked)
    @Update
    void updateItem(GroceryItemEntity item);

    // Deletes item
    @Delete
    void deleteItem(GroceryItemEntity item);
}
