package com.example.cen4360_final_project;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

//Defines the database and the tables it includes
@Database(entities = {GroceryListEntity.class, GroceryItemEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    //Holds the single database instance (Singleton)
    private static AppDatabase INSTANCE;

    //Expose DAOs to the rest of the app
    public abstract ListDao listDao();
    public abstract ItemDao itemDao();

    //Creates or returns the single database instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "grocery_database"
                    )
                    .allowMainThreadQueries() // simplifies threading for this project
                    .fallbackToDestructiveMigration() // recreate DB if schema changes
                    .build();
        }
        return INSTANCE;
    }
}
