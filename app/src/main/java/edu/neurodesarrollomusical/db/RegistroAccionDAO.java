package edu.neurodesarrollomusical.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RegistroAccionDAO {
    @Query("SELECT * FROM registro_accion_entity LIMIT 1000")
    List<RegistroAccionEntity> getAllBatch();

    @Query("SELECT * FROM registro_accion_entity")
    List<RegistroAccionEntity> getAll();

    @Insert
    void insertAll(RegistroAccionEntity... registros);

    @Insert
    void insert(RegistroAccionEntity registro);

    @Delete
    void delete(RegistroAccionEntity registro);

    @Delete
    void deleteAll(RegistroAccionEntity... registros);
}
