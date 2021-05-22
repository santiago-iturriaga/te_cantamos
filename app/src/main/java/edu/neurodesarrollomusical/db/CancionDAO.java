package edu.neurodesarrollomusical.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CancionDAO {
    @Query("SELECT * FROM cancion_entity")
    List<CancionEntity> getAll();

    @Query("SELECT * FROM cancion_entity WHERE id=:id")
    CancionEntity get(int id);

    @Insert
    void insert(CancionEntity cancion);

    @Insert
    void insertAll(CancionEntity... cancion);

    @Update
    void update(CancionEntity cancion);

    @Delete
    void delete(CancionEntity cancion);

    @Delete
    void deleteAll(CancionEntity... cancion);
}
