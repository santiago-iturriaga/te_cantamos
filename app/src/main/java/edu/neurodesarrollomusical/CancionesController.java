package edu.neurodesarrollomusical;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CancionesController {
    private static CancionesController _instance;

    public static CancionesController getInstance(Context context) {
        //if (_instance == null) {
            _instance = new CancionesController(context);
        //}
        return _instance;
    }

    public class Cancion {
        public final int id;
        public final String titulo;
        public final String autor;
        public final String letra;
        public final int resourceId;

        public Cancion(int id, String titulo, String autor, String letra, int resourceId) {
            this.id = id;
            this.titulo = titulo;
            this.autor = autor;
            this.letra = letra;
            this.resourceId = resourceId;
        }
    }

    private static int NUMERO_CANCIONES;
    private ArrayList<Cancion> _canciones;

    private CancionesController(Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        Resources resources = context.getApplicationContext().getResources();

        _canciones = new ArrayList<Cancion>();

        NUMERO_CANCIONES = resources.getInteger(R.integer.canciones_cant);

        for (int i = 0; i < NUMERO_CANCIONES; i++) {
            Cancion c;
            int tituloStringId = resources.getIdentifier("cancion" + i + "_titulo", "string", packageName);
            int autorStringId = resources.getIdentifier("cancion" + i + "_autor", "string", packageName);
            int letraStringId = resources.getIdentifier("cancion" + i + "_letra", "string", packageName);
            int mp3RawId = resources.getIdentifier("m" + i, "raw", packageName);
            if (tituloStringId != 0 && letraStringId != 0 && mp3RawId != 0) {
                c = new Cancion(i, resources.getString(tituloStringId), resources.getString(autorStringId), resources.getString(letraStringId), mp3RawId);
                _canciones.add(c);
            }
        }
    }

    public Cancion[] listarCanciones() {
        Cancion lista[] = new Cancion[_canciones.size()];
        return _canciones.toArray(lista);
    }

    public Cancion obtenerCancion(int i) {
        if (i < _canciones.size()) {
            return _canciones.get(i);
        } else {
            return null;
        }
    }
}
