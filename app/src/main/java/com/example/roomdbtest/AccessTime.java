package com.example.roomdbtest;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "test")
public class AccessTime {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "access_time")
    private String accessTime;

    public AccessTime(String accessTime){
        this.accessTime = accessTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAccessTime(String accessTime) {
        this.accessTime = accessTime;
    }

    public String getAccessTime() {
        return accessTime;
    }
}
