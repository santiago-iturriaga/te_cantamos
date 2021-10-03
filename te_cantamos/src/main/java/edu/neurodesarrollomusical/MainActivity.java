package edu.neurodesarrollomusical;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.Random;

import edu.neurodesarrollomusical.controller.CancionesController;
import edu.neurodesarrollomusical.controller.ConfigController;
import edu.neurodesarrollomusical.controller.SeguridadController;

public class MainActivity extends AppCompatActivity {
    static final public String EXTRA_CANCIONES = "CANCIONES";

    public enum MODO {
        SIN_ACCION,
        PLAY_CANCIONES_ELEGIDAS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent( this, LogExportService.class));

        Switch switchKaraoke = findViewById(R.id.mainSwitchKaraoke);
        switchKaraoke.setChecked(ConfigController.getInstance().getKaraoke(getApplicationContext()));
        switchKaraoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConfigController.getInstance().setKaraoke(getApplicationContext(), isChecked);
            }
        });

        Button buttonDebug = findViewById(R.id.mainButtonDebug);
        buttonDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DebugActivity.class));
            }
        });

        Button buttonAyuda = findViewById(R.id.mainButtonAyuda);
        buttonAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setPressed(true);
                startActivity(new Intent(MainActivity.this, AyudaActivity.class));
            }
        });

        Button buttonPlayFavoritos = findViewById(R.id.mainButtonPlayFavoritos);
        buttonPlayFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int max = v.getContext().getResources().getInteger(R.integer.intervencion_max_canciones);
                int canciones[] = new int[max];

                CancionesController.Cancion fav[] = CancionesController.getInstance(v.getContext()).listarCancionesFavoritas();

                if (fav.length > 0) {
                    if (fav.length > max) {
                        CancionesController.Cancion aux;
                        Random rnd = new Random();
                        for (int i = 0; i < max; i++) {
                            int pos = Math.abs(rnd.nextInt()) % (fav.length - i);
                            int last = fav.length - i - 1;

                            if (pos != last) {
                                aux = fav[last];
                                fav[last] = fav[pos];
                                fav[pos] = aux;
                            }
                        }

                        int j = 0;
                        for (int i = fav.length - max; i < fav.length; i++) {
                            canciones[j] = fav[i].id;
                            j++;
                        }
                    } else {
                        for (int i = 0; i < max; i++) {
                            canciones[i] = fav[i % fav.length].id;
                        }
                    }

                    Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                    i.putExtra(PlayerActivity.EXTRA_MODO, PlayerActivity.MODO.INTERVENCION.ordinal());
                    i.putExtra(PlayerActivity.EXTRA_CANCIONES, canciones);
                    startActivityForResult(i, MODO.SIN_ACCION.ordinal());
                } else {
                    MensajesHelper.showText(v.getContext(),"No hay canciones favoritas seleccionadas");
                }
            }
        });

        Button buttonPlayAleatorio = findViewById(R.id.mainButtonPlayAleatorio);
        buttonPlayAleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int max = v.getContext().getResources().getInteger(R.integer.intervencion_max_canciones);
                CancionesController.Cancion todas[] = CancionesController.getInstance(v.getContext()).listarCanciones();
                if (max > todas.length) max = todas.length;
                CancionesController.Cancion aux;
                Random rnd = new Random();
                for (int i = 0; i < max; i++) {
                    int pos = Math.abs(rnd.nextInt()) % (todas.length - i);
                    int last = todas.length-i-1;

                    if (pos != last) {
                        aux = todas[last];
                        todas[last] = todas[pos];
                        todas[pos] = aux;
                    }
                }

                int canciones[] = new int[max];
                int j = 0;
                for (int i=todas.length-max; i<todas.length; i++) {
                    canciones[j] = todas[i].id;
                    j++;
                }

                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                i.putExtra(PlayerActivity.EXTRA_MODO, PlayerActivity.MODO.INTERVENCION.ordinal());
                i.putExtra(PlayerActivity.EXTRA_CANCIONES, canciones);
                startActivityForResult(i, MODO.SIN_ACCION.ordinal());
            }
        });

        Button buttonListaCanciones = findViewById(R.id.mainButtonListaCanciones);
        buttonListaCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaCancionesActivity.class);
                i.putExtra(ListaCancionesActivity.EXTRA_MODO, ListaCancionesActivity.MODO.LISTAR_CANCIONES.ordinal());
                startActivityForResult(i, MODO.SIN_ACCION.ordinal());
            }
        });

        Button buttonPlayElegidas = findViewById(R.id.mainButtonPlayElegidas);
        buttonPlayElegidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaCancionesActivity.class);
                i.putExtra(ListaCancionesActivity.EXTRA_MODO, ListaCancionesActivity.MODO.ELEGIR_CANCIONES_PARA_INTERVENCION.ordinal());
                startActivityForResult(i, MODO.PLAY_CANCIONES_ELEGIDAS.ordinal());
            }
        });
        Button buttonPlaySoloUna = findViewById(R.id.mainButtonPlaySoloUna);
        buttonPlaySoloUna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListaCancionesActivity.class);
                i.putExtra(ListaCancionesActivity.EXTRA_MODO, ListaCancionesActivity.MODO.ELEGIR_UNA_CANCION_PARA_INTERVENCION.ordinal());
                startActivityForResult(i, MODO.PLAY_CANCIONES_ELEGIDAS.ordinal());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SeguridadController.getInstance().checkAutenticado(getApplicationContext()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (MODO.values()[requestCode]) {
                case SIN_ACCION:
                    // Nada para hacer?
                    break;
                case PLAY_CANCIONES_ELEGIDAS:
                    int[] elegidas = data.getIntArrayExtra(MainActivity.EXTRA_CANCIONES);
                    Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                    i.putExtra(PlayerActivity.EXTRA_MODO, PlayerActivity.MODO.INTERVENCION.ordinal());
                    i.putExtra(PlayerActivity.EXTRA_CANCIONES, elegidas);
                    startActivityForResult(i, MODO.SIN_ACCION.ordinal());
                    break;
            }
        }
    }
}