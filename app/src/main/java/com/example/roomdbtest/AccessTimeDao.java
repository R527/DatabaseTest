package com.example.roomdbtest;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AccessTimeDao {

    @Query("SELECT * FROM accessTime")
    List<AccessTime> getAll();

    @Query("SELECT * FROM accessTime WHERE id IN (:ids)")
    List<AccessTime> loadAllByIds(int[] ids);
    @Insert
    void insertAll(AccessTime... accessTimes);

    @Insert
    void insert(AccessTime accessTime);

    @Delete
    void delete(AccessTime accessTime);

}



