package com.susmoy.roomdatabasebasics;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ListDataDao {
    @Insert(onConflict = REPLACE)
    void insert(ListData listData);

    @Delete
    void delete(ListData listData);

    @Delete
    void reset(List<ListData> listData);

    @Query("UPDATE test_table SET text = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    @Query("SELECT * FROM test_table")
    List<ListData> getAll();
}
