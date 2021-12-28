package com.susmoy.roomdatabasebasics;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ListData.class}, version = 1, exportSchema = false)
public abstract class RoomDBContext extends RoomDatabase{
    private static RoomDBContext database;
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDBContext getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDBContext.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract ListDataDao  listDataDao();
}
