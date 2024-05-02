package com.example.murica_for_dummies.database;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {
    public static final Migration MIGRATION_1_3 = new Migration(1, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new 'cities' column in the UserTable
            database.execSQL("ALTER TABLE UsersTable ADD COLUMN savedUserLogin TEXT DEFAULT '0'");
            //database.execSQL("ALTER TABLE UsersTable ADD COLUMN review TEXT DEFAULT '0'");

            // Note: If there are other changes between version 1 and version 3,
            //       you would add additional migration steps here.
        }
    };
}