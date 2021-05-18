package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class PlayerActivity extends AppCompatActivity {
    static final public String EXTRA_CANCIONES = "CANCIONES";
    static final public String EXTRA_MODO = "MODO";

    public enum MODO {
        INTERVENCION,
        SOLO_LETRA
    }

    MediaPlayer player;
    ImageView imageViewFav;
    ImageButton buttonPlayPause, buttonNext, buttonPrev;
    TextView textViewTitulo, textViewAutor, textViewInterprete, textViewLetra, textViewTiempo;
    LinearLayout layoutProgreso;

    int[] canciones;
    int cancion_actual;
    MODO modo;

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

        imageViewFav = findViewById(R.id.playerImageViewFavorita);
        imageViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancion_actual >= 0 && canciones != null) {
                    CancionesController.Cancion c = CancionesController.getInstance(v.getContext()).obtenerCancionId(canciones[cancion_actual]);
                    if (c != null) {
                        c.es_favorita = !c.es_favorita;
                        setFavorito(imageViewFav, c.es_favorita);
                    }
                }
            }
        });

        layoutProgreso = findViewById(R.id.playerLayoutProgreso);
        textViewTitulo = findViewById(R.id.playerTextViewTitulo);
        textViewAutor = findViewById(R.id.playerTextViewAutor);
        textViewInterprete = findViewById(R.id.playerTextViewInterprete);
        textViewLetra = findViewById(R.id.playerTextViewLetra);
        textViewTiempo = findViewById(R.id.playerTextViewTiempo);

        Bundle extras = getIntent().getExtras();
        int modoOrd = extras.getInt(EXTRA_MODO, PlayerActivity.MODO.SOLO_LETRA.ordinal());
        modo = PlayerActivity.MODO.values()[modoOrd];

        //MensajesHelper.showText(this, Arrays.toString(canciones));

        switch (modo) {
            case INTERVENCION:
                canciones = extras.getIntArray(EXTRA_CANCIONES);
                cancion_actual = 0;

                buttonPlayPause.setVisibility(View.VISIBLE);
                buttonNext.setVisibility(View.VISIBLE);
                buttonPrev.setVisibility(View.VISIBLE);
                layoutProgreso.setVisibility(View.VISIBLE);
                textViewTiempo.setVisibility(View.VISIBLE);
                start();
                break;
            case SOLO_LETRA:
                canciones = extras.getIntArray(EXTRA_CANCIONES);
                cancion_actual = 0;

                CancionesController.Cancion c = CancionesController.getInstance(this.getApplicationContext()).obtenerCancionId(canciones[cancion_actual]);
                if (c != null) {
                    textViewTitulo.setText(c.titulo);
                    textViewAutor.setText(c.autor);
                    textViewInterprete.setText(c.interprete);
                    textViewLetra.setText(c.letra);
                    setFavorito(imageViewFav, c.es_favorita);
                }

                layoutProgreso.setVisibility(View.INVISIBLE);
                textViewTiempo.setVisibility(View.INVISIBLE);
                buttonPlayPause.setVisibility(View.INVISIBLE);
                buttonNext.setVisibility(View.INVISIBLE);
                buttonPrev.setVisibility(View.INVISIBLE);
                stop();
                break;
        }
    }

    public void setFavorito(ImageView fav, boolean estado) {
        if (estado) {
            fav.setImageResource(R.drawable.ic_cancion_favorita);
        } else {
            fav.setImageResource(R.drawable.ic_cancion_no_favorita);
        }
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

        if (canciones != null && modo == MODO.INTERVENCION) {
            if (canciones.length > 0 && cancion_actual < canciones.length) {
                CancionesController.Cancion c = CancionesController.getInstance(this.getApplicationContext()).obtenerCancionId(canciones[cancion_actual]);
                if (c != null) {
                    textViewTitulo.setText(c.titulo);
                    textViewAutor.setText(c.autor);
                    textViewInterprete.setText(c.interprete);
                    textViewLetra.setText(c.letra);
                    setFavorito(imageViewFav, c.es_favorita);

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