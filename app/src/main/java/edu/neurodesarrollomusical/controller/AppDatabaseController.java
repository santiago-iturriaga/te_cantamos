package edu.neurodesarrollomusical.controller;

import android.content.Context;

import androidx.room.Room;

import edu.neurodesarrollomusical.db.AppDatabase;

public class AppDatabaseController {
    private static AppDatabaseController _instance;

    public synchronized static AppDatabaseController getInstance(Context context) {
        if (_instance == null) {
            _instance = new AppDatabaseController(context);
        }
        return _instance;
    }

    AppDatabase _db;

    private AppDatabaseController(Context context) {
        _db = Room.databaseBuilder(context, AppDatabase.class, "neurodesarrollo_musical").build();
    }

    public AppDatabase getDB() {
        return _db;
    }
}
