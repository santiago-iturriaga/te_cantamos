package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class PlayerActivity extends AppCompatActivity {
    static final public String EXTRA_CANCIONES = "CANCIONES";

    MediaPlayer player;
    ImageButton buttonPlayPause, buttonNext, buttonPrev;
    TextView textViewTitulo, textViewAutor, textViewLetra;

    int[] canciones;
    int cancion_actual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        buttonPlayPause = findViewById(R.id.playerImageViewPlayControl);
        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    if (player.isPlaying()) pause();
                    else play();
                }
            }
        });

        buttonNext = findViewById(R.id.playerImageViewNextControl);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancion_actual < canciones.length - 1) cancion_actual++;
                start();
            }
        });

        buttonPrev = findViewById(R.id.playerImageViewPrevControl);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancion_actual > 0) cancion_actual--;
                start();
            }
        });

        textViewTitulo = findViewById(R.id.playerTextViewTitulo);
        textViewAutor = findViewById(R.id.playerTextViewAutor);
        textViewLetra = findViewById(R.id.playerTextViewLetra);

        Bundle extras = getIntent().getExtras();
        canciones = extras.getIntArray(EXTRA_CANCIONES);
        cancion_actual = 0;

        MensajesHelper.showText(this, Arrays.toString(canciones));

        start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    protected void start() {
        stop();

        if (canciones != null) {
            if (canciones.length > 0 && cancion_actual < canciones.length) {
                CancionesController.Cancion c = CancionesController.getInstance(this.getApplicationContext()).obtenerCancion(canciones[cancion_actual]);
                if (c != null) {
                    textViewTitulo.setText(c.titulo);
                    textViewAutor.setText(c.autor);
                    textViewLetra.setText(c.letra);

                    player = MediaPlayer.create(PlayerActivity.this, c.resourceId);
                    player.start();
                    buttonPlayPause.setBackgroundResource(R.drawable.ic_pause_control);
                }
            }
        }
    }

    protected void stop() {
        if (player != null) {
            buttonPlayPause.setBackgroundResource(R.drawable.ic_play_control);
            if (player.isPlaying()) player.stop();
            player.release();
        }
    }

    protected void play() {
        if (player != null) if (!player.isPlaying()) {
            player.start();
            buttonPlayPause.setBackgroundResource(R.drawable.ic_pause_control);
        }
    }

    protected void pause() {
        if (player != null) if (player.isPlaying()) {
            player.pause();
            buttonPlayPause.setBackgroundResource(R.drawable.ic_play_control);
        }
    }
}