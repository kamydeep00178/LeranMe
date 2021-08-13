package com.example.learnme.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/* Database abstract class for define all entities and dao */
@Database(entities = {DataModel.class},version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
