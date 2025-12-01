package com.example.cen4360_final_project;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface ListDao {

    //Inserts a new grocery list
    @Insert
    long insertList(GroceryListEntity list);

    //Get all grocery lists in alphabetical order
    @Query("SELECT * FROM grocery_lists ORDER BY listName ASC")
    List<GroceryListEntity> getAllLists();

    //Updates list name
    @Update
    void updateList(GroceryListEntity list);

    //Deletes a list
    @Delete
    void deleteList(GroceryListEntity list);
}
