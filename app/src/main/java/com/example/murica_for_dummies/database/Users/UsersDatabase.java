package com.example.murica_for_dummies.database.Users;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.murica_for_dummies.database.entities.Users;
import com.example.murica_for_dummies.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {Users.class}, version = 1, exportSchema = false)
public abstract class UsersDatabase extends RoomDatabase {

    public static final String USERS_TABLE = "usersTable";

    public static final String DATABASE_NAME = "Users_database";

    private static volatile UsersDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static UsersDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (UsersDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UsersDatabase.class,DATABASE_NAME)
                            .fallbackToDestructiveMigration().addCallback(addDefaultValues).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(()->{
                UsersDAO dao = INSTANCE.usersDAO();
                dao.deleteAll();
                Users admin = new Users("admin1", "admin1", true);
                dao.insert(admin);

                Users testUser1 = new Users("testuser1", "testuser1", false);
                dao.insert(testUser1);
            });
        }
    };

    public abstract UsersDAO usersDAO();
}


