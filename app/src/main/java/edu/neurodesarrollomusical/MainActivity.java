package edu.neurodesarrollomusical;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button buttonAyuda = findViewById(R.id.mainButtonAyuda);
        buttonAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setPressed(true);
                Intent i = new Intent(MainActivity.this, AyudaActivity.class);
                startActivity(i);
            }
        });

        Button buttonPlayAleatorio = findViewById(R.id.mainButtonPlayAleatorio);
        buttonPlayAleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                int canciones[] = {0,1,2,3,4,5};
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK) {
            switch (MODO.values()[requestCode]) {
                case SIN_ACCION:
                    // Nada para hacer?
                    break;
                case PLAY_CANCIONES_ELEGIDAS:
                    //leer las canciones seleccionadas que vienen en "RESULT"
                    //abrir el player desde acá pasando por el intent las canciones seleccionadas
                    int[] elegidas = data.getIntArrayExtra(MainActivity.EXTRA_CANCIONES);
                    Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                    i.putExtra(PlayerActivity.EXTRA_CANCIONES, elegidas);
                    startActivityForResult(i, MODO.SIN_ACCION.ordinal());

                    break;
            }
        }
    }
}