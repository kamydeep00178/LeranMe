package com.example.learnme.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* Entity Class , Info about the table */
@Entity
public class DataModel {


    //Primary key with auto generate
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo
    String title;
    @ColumnInfo
    String description;
    @ColumnInfo
    String pubDate;
    @ColumnInfo
    String link;

    public DataModel(String title, String description, String pubDate, String link) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getLink() {
        return link;
    }


}
