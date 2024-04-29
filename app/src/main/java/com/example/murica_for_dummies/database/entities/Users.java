package com.example.murica_for_dummies.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.murica_for_dummies.database.Users.UsersDatabase;

import java.time.LocalDateTime;

@Entity(tableName = UsersDatabase.USERS_TABLE)
public class Users {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String login; //used as a username

    private String password;

    private boolean isAdmin;

    private LocalDateTime dateOfCreation;

    private LocalDateTime lastConnection;

    public Users(String login, String password, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
        this.dateOfCreation = LocalDateTime.now();
        this.lastConnection = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDateTime getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(LocalDateTime lastConnection) {
        this.lastConnection = lastConnection;
    }
}
