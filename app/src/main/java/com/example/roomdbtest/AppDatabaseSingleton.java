package com.example.roomdbtest;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseSingleton {
    private static AppDatabase instace = null;

    public static AppDatabase getInstace(Context context){
        if(instace != null){
            return instace;
        }
        instace = Room.databaseBuilder(context,AppDatabase.class,"database-name").build();
        return instace;
    }
}
