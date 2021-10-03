package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import edu.neurodesarrollomusical.R;
import edu.neurodesarrollomusical.db.AppDatabase;
import edu.neurodesarrollomusical.db.CancionEntity;

public class CancionesController {
    private static CancionesController _instance;

    public synchronized static CancionesController getInstance(Context context) {
        if (_instance == null) {
            _instance = new CancionesController(context);
        }
        return _instance;
    }

    public class Cancion {
        public final int id;
        public final String titulo;
        public final String autor;
        public final String interprete;
        public final String letra;
        public final int resourceId;
        public final int resourceIdKaraoke;
        public CancionEntity entity;

        public Cancion(int id, String titulo, String autor, String interprete,
                       String letra, int resourceId, int resourceIdKaraoke) {
            this.id = id;
            this.titulo = titulo;
            this.autor = autor;
            this.interprete = interprete;
            this.letra = letra;
            this.resourceId = resourceId;
            this.resourceIdKaraoke = resourceIdKaraoke;
            this.entity = null;
        }

        public boolean getEsFavorita() {
            if (entity != null) {
                return entity.esFavorita;
            } else {
                return false;
            }
        }

        public void setEsFavorita(Context context, boolean favorita) {
            if (entity != null) {
                if (entity.esFavorita != favorita) {
                    entity.esFavorita = favorita;
                    AppDatabaseController.getInstance(context).getDB().cancionDAO().update(entity);
                }
            }
        }
    }

    private static int NUMERO_CANCIONES;
    private ArrayList<Cancion> _canciones;

    private CancionesController(Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        Resources resources = context.getApplicationContext().getResources();

        List<CancionEntity> cancionesDB = AppDatabaseController.getInstance(context).getDB().cancionDAO().getAll();

        _canciones = new ArrayList<Cancion>();

        //NUMERO_CANCIONES = resources.getInteger(R.integer.canciones_cant);
        int[] cancionesIds = resources.getIntArray(R.array.canciones);
        NUMERO_CANCIONES = cancionesIds.length;

        for (int i = 0; i < NUMERO_CANCIONES; i++) {
            int id;
            id = cancionesIds[i];

            Cancion c;
            int tituloStringId = resources.getIdentifier("cancion" + id + "_titulo", "string", packageName);
            int autorStringId = resources.getIdentifier("cancion" + id + "_autor", "string", packageName);
            int interpreteStringId = resources.getIdentifier("cancion" + id + "_interprete", "string", packageName);
            int letraStringId = resources.getIdentifier("cancion" + id + "_letra", "string", packageName);
            int mp3RawId = resources.getIdentifier("c" + id, "raw", packageName);
            int mp3RawIdKaraoke = resources.getIdentifier("c" + id + "k", "raw", packageName);

            if (tituloStringId != 0 && letraStringId != 0 && mp3RawId != 0) {
                c = new Cancion(id, resources.getString(tituloStringId), resources.getString(autorStringId),
                        resources.getString(interpreteStringId),
                        resources.getString(letraStringId), mp3RawId, mp3RawIdKaraoke);

                for (int j = 0; j < cancionesDB.size() && c.entity==null; j++) {
                    if (cancionesDB.get(j).id == c.id) {
                        c.entity = cancionesDB.get(j);
                    }
                }
                if (c.entity == null) {
                    CancionEntity e = new CancionEntity();
                    e.cantidadVecesEsuchada = 0;
                    e.nombreCancion = c.titulo;
                    e.id = c.id;
                    e.esFavorita = false;
                    AppDatabaseController.getInstance(context).getDB().cancionDAO().insert(e);
                    c.entity = e;
                }

                _canciones.add(c);
            }
        }
    }

    public Cancion[] listarCanciones() {
        Cancion lista[] = new Cancion[_canciones.size()];
        return _canciones.toArray(lista);
    }

    public Cancion[] listarCancionesFavoritas() {
        ArrayList<Cancion> lista = new ArrayList<Cancion>();
        for (int i=0; i<_canciones.size(); i++) {
            if (_canciones.get(i).getEsFavorita()) {
                lista.add(_canciones.get(i));
            }
        }
        Cancion listaArray[] = new Cancion[lista.size()];
        return lista.toArray(listaArray);
    }

    public Cancion obtenerCancionId(int id) {
        for (int i = 0; i < NUMERO_CANCIONES; i++) {
            if (_canciones.get(i).id == id) return _canciones.get(i);
        }
        return null;

        /*if (i < _canciones.size()) {
            return _canciones.get(i);
        } else {
            return null;
        }*/
    }
}
