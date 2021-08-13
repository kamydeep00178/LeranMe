package com.example.learnme.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/* interface for query  */
@Dao
public interface NewsDao {

    //Get data from database
    @Query("SELECT * From datamodel")
    List<DataModel> getAll();

    //Insert data into database
    @Insert
    void insert(DataModel dataModel);

    //Remove From Database
    @Delete
    void delete(DataModel dataModel);

}
