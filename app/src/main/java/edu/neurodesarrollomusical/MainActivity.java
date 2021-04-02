package edu.neurodesarrollomusical;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public enum MODO {
        SIN_ACCION,
        PLAY_CANCIONES_ELEGIDAS
    }

    private void showText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAyuda = findViewById(R.id.mainButtonAyuda);
        buttonAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Ayuda!");
                Intent i = new Intent(MainActivity.this, AyudaActivity.class);
                startActivity(i);
            }
        });


        Button buttonPlayAleatorio = findViewById(R.id.mainButtonPlayAleatorio);
        buttonPlayAleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Play aleatorio!");
            }
        });

        Button buttonListaCanciones = findViewById(R.id.mainButtonListaCanciones);
        buttonListaCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Lista de canciones!");
                Intent i = new Intent(MainActivity.this, ListaCancionesActivity.class);
                i.putExtra("MODO", ListaCancionesActivity.MODO.LISTAR_CANCIONES.ordinal());
                startActivityForResult(i, MODO.SIN_ACCION.ordinal());
            }
        });

        Button buttonPlayElegidas = findViewById(R.id.mainButtonPlayElegidas);
        buttonPlayElegidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Play elegidas!");
                Intent i = new Intent(MainActivity.this, ListaCancionesActivity.class);
                i.putExtra("MODO", ListaCancionesActivity.MODO.ELEGIR_CANCIONES_PARA_INTERVENCION.ordinal());
                startActivityForResult(i, MODO.PLAY_CANCIONES_ELEGIDAS.ordinal());
            }
        });
        Button buttonPlaySoloUna = findViewById(R.id.mainButtonPlaySoloUna);
        buttonPlaySoloUna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Play solo una!");
                Intent i = new Intent(MainActivity.this, ListaCancionesActivity.class);
                i.putExtra("MODO", ListaCancionesActivity.MODO.ELEGIR_UNA_CANCION_PARA_INTERVENCION.ordinal());
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
                    //leer las canciones seleccionadas que vienen en "data"
                    //abrir el player desde acá pasando por el intent las canciones seleccionadas
                    //i.putExtra("modo", RESULT_SOLO_UNA);

                    Bundle returnedResult = data.getExtras();

                    break;
            }
        }
    }
}