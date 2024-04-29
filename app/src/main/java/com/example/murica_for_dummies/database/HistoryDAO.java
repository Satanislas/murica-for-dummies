package com.example.murica_for_dummies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.murica_for_dummies.database.entities.History;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertHistory(History history);

    @Query("SELECT * FROM historyTable")
    LiveData<List<History>> getAllHistory();
}
