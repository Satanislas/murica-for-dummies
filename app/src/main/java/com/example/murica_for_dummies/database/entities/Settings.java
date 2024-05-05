package com.example.murica_for_dummies.database.entities;

import android.graphics.ColorSpace;
import android.text.SpannableString;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settingsTable")
public class Settings {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;

    private int colorId;

    public Settings(String username, int colorId) {
        this.username = username;
        this.colorId = colorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
