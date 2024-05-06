package com.example.murica_for_dummies.database.Settings;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.murica_for_dummies.database.Users.UsersDatabase;
import com.example.murica_for_dummies.database.entities.Settings;

import java.util.List;
import java.util.Set;

@Dao
public interface SettingsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSettings(Settings settings);

    @Query("DELETE from settingsTable")
    void deleteAll();

    @Query("UPDATE settingsTable SET colorId = :colorId WHERE username = :username")
    void updateColorByUsername(String username, int colorId);

    @Query("SELECT * FROM settingsTable WHERE username = :username")
    LiveData<List<Settings>> showColorRGBByUsername(String username);

    @Query("SELECT * FROM settingsTable WHERE username = :username")
    Settings showSettingsByUsername(String username);
}
