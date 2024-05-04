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

    private String colorName;
    private String colorRgb;

    public Settings(String username, String colorName, String colorRgb) {
        this.username = username;
        this.colorName = colorName;
        this.colorRgb = colorRgb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorRgb() {
        return colorRgb;
    }

    public void setColorRgb(String colorRgb) {
        this.colorRgb = colorRgb;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
