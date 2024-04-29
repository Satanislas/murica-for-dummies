package com.example.murica_for_dummies.database.Users;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.murica_for_dummies.database.entities.Users;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class UsersRepository {
    private static UsersRepository instance;
    private UsersDAO usersDAO;
    private List<Users> allLogs;

    private UsersRepository(Application application) {
        UsersDatabase db = UsersDatabase.getDatabase(application);
        this.usersDAO = db.usersDAO();
        this.allLogs = (List<Users>) this.usersDAO.getAllRecords();
    }

    public static UsersRepository getInstance(Application application) {
        if (instance == null) {
            synchronized (UsersRepository.class) {
                if (instance == null) {
                    instance = new UsersRepository(application);
                }
            }
        }
        return instance;
    }



    public static UsersRepository getRepository(Application application) {
        Future<UsersRepository> future = UsersDatabase.databaseWriteExecutor.submit(
                new Callable<UsersRepository>() {
                    @Override
                    public UsersRepository call() throws Exception {
                        return new UsersRepository(application);
                    }
                }
        );
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isValidLogin(String username, String password) {
        for (Users user : allLogs) {
            if (user.getLogin().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }



    public LiveData<Users> getUserByUserName(String username) {
        return usersDAO.getUserByUserName(username);
    }

    public LiveData<Users> getUserByUserId(int id) {
        return usersDAO.getUserByUserId(id);
    }

    public void insertUser(Users user) {
        UsersDatabase.databaseWriteExecutor.execute(() -> {
            usersDAO.insert(user);
        });
    }

    public void deleteAllUsers() {
        UsersDatabase.databaseWriteExecutor.execute(() -> {
            usersDAO.deleteAll();
        });
    }

    public void createUser(String username, String password, boolean isAdmin, CreateUserCallback callback) {
        UsersDatabase.databaseWriteExecutor.execute(() -> {
            LiveData<Users> existingUserLiveData = getUserByUserName(username);
            existingUserLiveData.observeForever(existingUser -> {
                existingUserLiveData.removeObserver(observer -> {});
                if (existingUser == null) {
                    Users newUser = new Users(username, password, isAdmin);
                    insertUser(newUser);
                    callback.onUserCreated(newUser);
                } else {
                    callback.onUsernameExists();
                }
            });
        });
    }

    public interface CreateUserCallback {
        void onUserCreated(Users user);
        void onUsernameExists();
    }
}
