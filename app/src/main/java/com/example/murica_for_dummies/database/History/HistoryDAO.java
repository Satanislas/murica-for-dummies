package com.example.murica_for_dummies.database.History;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.murica_for_dummies.database.Users.UsersDatabase;
import com.example.murica_for_dummies.database.entities.History;

import java.util.List;

@Dao
public interface HistoryDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertHistory(History history);

    @Query("DELETE from historyTable")
    void deleteAll();

    @Query("SELECT * FROM historyTable WHERE savedUserLogin = :savedUserLogin")
    LiveData<List<History>> getHistoryByUser(String savedUserLogin);
}
