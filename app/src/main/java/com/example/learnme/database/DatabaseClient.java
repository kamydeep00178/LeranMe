package com.example.learnme.database;

import android.content.Context;
import androidx.room.Room;

/* Database Client and
make it singleton **/
public class DatabaseClient {
    private final Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private final NewsDatabase appDatabase;

    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, NewsDatabase.class, "NewsFeed").build();
    }

    /* singleton instance of database **/
    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public NewsDatabase getAppDatabase() {
        return appDatabase;
    }
}
