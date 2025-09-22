package edu.neurodesarrollomusical.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cancion_entity")
public class CancionEntity {
    @PrimaryKey
    public int id;

    @ColumnInfo(name="nombre_cancion")
    public String nombreCancion;

    @ColumnInfo(name="cantidad_veces_esuchada")
    public int cantidadVecesEsuchada;

    @ColumnInfo(name="es_favorita")
    public boolean esFavorita;
}
