package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class PlayerActivity extends AppCompatActivity {

    private void showText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        TextView textViewTitulo = findViewById(R.id.playerTextViewTitulo);
        TextView textViewLetra = findViewById(R.id.playerTextViewLetra);

        Bundle extras = getIntent().getExtras();
        int[] canciones = extras.getIntArray("CANCIONES");

        showText(Arrays.toString(canciones));

        if (canciones.length > 0) {
            CancionesController.Cancion c = CancionesController.getInstance(this.getApplicationContext()).obtenerCancion(canciones[0]);
            if (c != null) {
                textViewTitulo.setText(c.titulo);
                textViewLetra.setText(c.letra);
                MediaPlayer ring = MediaPlayer.create(PlayerActivity.this, c.resourceId);
                ring.start();
            }
        }
    }
}