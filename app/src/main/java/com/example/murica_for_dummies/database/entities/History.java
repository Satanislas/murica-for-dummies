package com.example.murica_for_dummies.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "historyTable")
public class History {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String savedUserLogin;
    private String unit1Name;
    private String unit2Name;
    private double value;

    public History(String savedUserLogin, String unit1Name, String unit2Name, double value) {
        this.savedUserLogin = savedUserLogin;
        this.unit1Name = unit1Name;
        this.unit2Name = unit2Name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSavedUserLogin() {
        return savedUserLogin;
    }

    public void setSavedUserLogin(String savedUserLogin) {
        this.savedUserLogin = savedUserLogin;
    }

    public String getUnit1Name() {
        return unit1Name;
    }

    public void setUnit1Name(String unit1Name) {
        this.unit1Name = unit1Name;
    }

    public String getUnit2Name() {
        return unit2Name;
    }

    public void setUnit2Name(String unit2Name) {
        this.unit2Name = unit2Name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
