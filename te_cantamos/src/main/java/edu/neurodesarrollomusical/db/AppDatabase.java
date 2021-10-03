package edu.neurodesarrollomusical.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {CancionEntity.class, RegistroAccionEntity.class}, version = 2) //, exportSchema = false
public abstract class AppDatabase extends RoomDatabase {
        public abstract CancionDAO cancionDAO();
        public abstract RegistroAccionDAO registroAccionDAO();
}
