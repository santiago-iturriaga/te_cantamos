package edu.neurodesarrollomusical;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int RESULT_LISTA_CANCIONES = 100;
    private final int RESULT_ELEGIDAS = 101;
    private final int RESULT_SOLO_UNA = 102;

    private void showText(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAyuda = findViewById(R.id.buttonAyuda);
        buttonAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Ayuda!");
            }
        });


        Button buttonPlayAleatorio = findViewById(R.id.buttonPlayAleatorio);
        buttonPlayAleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Play aleatorio!");
            }
        });

        Button buttonListaCanciones = findViewById(R.id.buttonListaCanciones);
        buttonListaCanciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Lista de canciones!");
                Intent i = new Intent (MainActivity.this, ListaCancionesActivity.class);
                i.putExtra("modo", RESULT_LISTA_CANCIONES);
                startActivityForResult(i, RESULT_LISTA_CANCIONES);
            }
        });

        Button buttonPlayElegidas = findViewById(R.id.buttonPlayElegidas);
        buttonPlayElegidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Play elegidas!");
                Intent i = new Intent (MainActivity.this, ListaCancionesActivity.class);
                i.putExtra("modo", RESULT_ELEGIDAS);
                startActivityForResult(i, RESULT_ELEGIDAS);
            }
        });
        Button buttonPlaySoloUna = findViewById(R.id.buttonPlaySoloUna);
        buttonPlaySoloUna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showText("Play solo una!");
                Intent i = new Intent (MainActivity.this, ListaCancionesActivity.class);
                i.putExtra("modo", RESULT_SOLO_UNA);
                startActivityForResult(i, RESULT_SOLO_UNA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Intent i = new Intent(MainActivity.this, PlayerActivity.class);
            switch (resultCode){
                case RESULT_ELEGIDAS:
                    //leer las canciones seleccionadas que vienen en "data"
                    //abrir el player desde acá pasando por el intent las canciones seleccionadas
                    i.putExtra("modo", RESULT_ELEGIDAS);
                    break;
                case RESULT_LISTA_CANCIONES:
                    //leer las canciones seleccionadas que vienen en "data"
                    //abrir el player desde acá pasando por el intent las canciones seleccionadas
                    i.putExtra("modo", RESULT_LISTA_CANCIONES);
                    break;
                case RESULT_SOLO_UNA:
                    //leer las canciones seleccionadas que vienen en "data"
                    //abrir el player desde acá pasando por el intent las canciones seleccionadas
                    i.putExtra("modo", RESULT_SOLO_UNA);
                    break;
            }
        }
    }
}