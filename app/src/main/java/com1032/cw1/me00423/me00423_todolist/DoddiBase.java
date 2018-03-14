package com1032.cw1.me00423.me00423_todolist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Doddibase is this app's database.
 * Within it, there exist two tables delineated in different classes: ListItems and Passcode.
 * Building and connecting to Room-based databaseBuilder
 *
 * Created by Doddi Elgabry on 11/03/2018.
 */

@Database(entities = {ListItems.class, Passcode.class}, version = 1, exportSchema = false)
public abstract class DoddiBase extends RoomDatabase {

    private static DoddiBase INSTANCE;

    public abstract DoddiBaseDao DoddiData();

    public static DoddiBase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), DoddiBase.class).build();
        }
        return INSTANCE;
    }

    public static DoddiBase getFileDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DoddiBase.class, "DoddiBase").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
