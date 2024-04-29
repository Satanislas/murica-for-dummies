package com.example.murica_for_dummies.database.History;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.murica_for_dummies.database.entities.History;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HistoryRepository {
    private static HistoryRepository instance;
    private HistoryDAO historyDAO;
    private LiveData<List<History>> allHistory;

    private HistoryRepository(Application application, String tableName) {
        HistoryDatabase db = HistoryDatabase.getDatabase(application, tableName);
        this.historyDAO = db.historyDAO();
        this.allHistory = this.historyDAO.getAllHistory();
    }

    public static HistoryRepository getInstance(Application application, String tableName) {
        if (instance == null) {
            synchronized (HistoryRepository.class) {
                if (instance == null) {
                    instance = new HistoryRepository(application, tableName);
                }
            }
        }
        return instance;
    }

    public static HistoryRepository getRepository(Application application, String tableName) {
        Future<HistoryRepository> future = HistoryDatabase.databaseWriteExecutor.submit(
                new Callable<HistoryRepository>() {
                    @Override
                    public HistoryRepository call() throws Exception {
                        return new HistoryRepository(application, tableName);
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

    public LiveData<List<History>> getAllHistory() {
        return allHistory;
    }

    public void insertHistory(History history) {
        HistoryDatabase.databaseWriteExecutor.execute(() -> {
            historyDAO.insertHistory(history);
        });
    }
}
