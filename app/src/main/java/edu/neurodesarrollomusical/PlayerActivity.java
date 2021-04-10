package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class PlayerActivity extends AppCompatActivity {
    static final public String EXTRA_CANCIONES = "CANCIONES";

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        TextView textViewTitulo = findViewById(R.id.playerTextViewTitulo);
        TextView textViewAutor = findViewById(R.id.playerTextViewAutor);
        TextView textViewLetra = findViewById(R.id.playerTextViewLetra);

        Bundle extras = getIntent().getExtras();
        int[] canciones = extras.getIntArray(EXTRA_CANCIONES);

        MensajesHelper.showText(this, Arrays.toString(canciones));

        if (canciones.length > 0) {
            CancionesController.Cancion c = CancionesController.getInstance(this.getApplicationContext()).obtenerCancion(canciones[0]);
            if (c != null) {
                textViewTitulo.setText(c.titulo);
                textViewAutor.setText(c.autor);
                textViewLetra.setText(c.letra);

                if (player != null) player.release();
                player = MediaPlayer.create(PlayerActivity.this, c.resourceId);
                player.start();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
    }
}