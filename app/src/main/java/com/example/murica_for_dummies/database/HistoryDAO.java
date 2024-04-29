package com.example.murica_for_dummies.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.murica_for_dummies.database.entities.Users;

import java.util.List;

@Dao
public interface HistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Users users);

    @Delete
    void delete(Users users);

    @Query("Select * from " + UsersDatabase.USERS_TABLE + " ORDER BY login")
    List<Users> getAllRecords();

    @Query("DELETE from " + UsersDatabase.USERS_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + UsersDatabase.USERS_TABLE + " WHERE login = :username AND password = :password")
    Users getUserByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM " + UsersDatabase.USERS_TABLE + " WHERE login == :username")
    LiveData<Users> getUserByUserName(String username);

    @Query("SELECT * FROM " + UsersDatabase.USERS_TABLE + " WHERE id == :userId")
    LiveData<Users> getUserByUserId(int userId);
}
