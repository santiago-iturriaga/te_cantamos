package edu.neurodesarrollomusical.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "registro_accion_entity")
public class RegistroAccionEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "fecha_accion")
    public Date fechaAccion;

    @ColumnInfo(name = "nombre_cancion")
    public String nombreCancion;

    @ColumnInfo(name = "numero_cancion")
    public int numeroCancion;

    @ColumnInfo(name = "accion_inicio")
    public boolean accionInicio;

    @ColumnInfo(name = "accion_fin")
    public boolean accionFin;

    public RegistroAccionEntity(Date fechaAccion, String nombreCancion, int numeroCancion, boolean accionInicio, boolean accionFin) {
        this.id = 0;
        this.fechaAccion = fechaAccion;
        this.nombreCancion = nombreCancion;
        this.numeroCancion = numeroCancion;
        this.accionInicio = accionInicio;
        this.accionFin = accionFin;
    }
}