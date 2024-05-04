package com.example.murica_for_dummies.database.Settings;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.murica_for_dummies.database.Users.UsersDatabase;
import com.example.murica_for_dummies.database.entities.Settings;

import java.util.List;

@Dao
public interface SettingsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSettings(Settings settings);

    @Query("DELETE from settingsTable")
    void deleteAll();

    @Query("UPDATE settingsTable SET colorRgb = :newColorRgb WHERE colorName = :colorName")
    void updateColor(String colorName, String newColorRgb);

    @Query("SELECT colorRgb FROM settingsTable WHERE colorName = :colorName")
    String showColorRGB(String colorName);
}
