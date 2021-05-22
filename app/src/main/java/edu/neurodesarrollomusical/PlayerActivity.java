package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import edu.neurodesarrollomusical.controller.AppDatabaseController;
import edu.neurodesarrollomusical.controller.CancionesController;
import edu.neurodesarrollomusical.controller.RegistroAccionesController;
import edu.neurodesarrollomusical.controller.SeguridadController;
import edu.neurodesarrollomusical.db.RegistroAccionEntity;

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
                if (cancion_actual < canciones.length - 1) {
                    start(cancion_actual+1);
                } else {
                    //stop();
                    finish();
                }
            }
        });

        buttonPrev = findViewById(R.id.playerImageViewPrevControl);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancion_actual > 0) {
                    start(cancion_actual-1);
                } else {
                    start(cancion_actual);
                }
            }
        });

        imageViewFav = findViewById(R.id.playerImageViewFavorita);
        imageViewFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancion_actual >= 0 && canciones != null) {
                    CancionesController.Cancion c = CancionesController.getInstance(v.getContext()).obtenerCancionId(canciones[cancion_actual]);
                    if (c != null) {
                        c.setEsFavorita(getApplicationContext(), !c.getEsFavorita());
                        setFavorito(imageViewFav, c.getEsFavorita());
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
                updateProgreso();

                final Handler handler = new Handler();
                final int delay = 1000;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (player != null) {
                                if (player.isPlaying()) {
                                    int duracion = player.getDuration();
                                    int posicion = player.getCurrentPosition();
                                    if (duracion > posicion) {
                                        textViewTiempo.setText(displayTime(duracion - posicion));
                                    }
                                }
                            }
                        } catch (IllegalStateException e) {
                            // Nada
                        }
                        handler.postDelayed(this, delay);
                    }
                }, delay);

                buttonPlayPause.setVisibility(View.VISIBLE);
                buttonNext.setVisibility(View.VISIBLE);
                buttonPrev.setVisibility(View.VISIBLE);
                layoutProgreso.setVisibility(View.VISIBLE);
                textViewTiempo.setVisibility(View.VISIBLE);
                start(cancion_actual);
                break;
            case SOLO_LETRA:
                canciones = extras.getIntArray(EXTRA_CANCIONES);
                cancion_actual = 0;

                CancionesController.Cancion c = CancionesController.getInstance(this.getApplicationContext()).obtenerCancionId(canciones[cancion_actual]);
                if (c != null) {
                    textViewTitulo.setText(c.titulo);
                    if (c.autor.trim().length() > 0) {
                        textViewAutor.setVisibility(View.VISIBLE);
                        textViewAutor.setText(c.autor);
                    } else {
                        textViewAutor.setVisibility(View.INVISIBLE);
                    }
                    if (c.interprete.trim().length() > 0 && !c.interprete.trim().equals(c.autor.trim())) {
                        textViewInterprete.setVisibility(View.VISIBLE);
                        textViewInterprete.setText(c.interprete);
                    } else {
                        textViewInterprete.setVisibility(View.INVISIBLE);
                    }
                    textViewLetra.setText(c.letra);
                    setFavorito(imageViewFav, c.getEsFavorita());
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

    private Boolean setImagenProgreso(ImageView p, int pos) {
        if (canciones.length > pos && canciones.length > 1) {
            p.setVisibility(View.VISIBLE);
            if (cancion_actual > pos) {
                p.setImageResource(R.drawable.ic_play_progreso_listo);
            } else {
                p.setImageResource(R.drawable.ic_play_progreso);
            }
            return true;
        }
        else {
            p.setVisibility(View.GONE);
            return false;
        }
    }

    private void updateProgreso() {
        ImageView p1 = findViewById(R.id.playerLayoutProgreso1);
        ImageView p2 = findViewById(R.id.playerLayoutProgreso2);
        ImageView p3 = findViewById(R.id.playerLayoutProgreso3);
        ImageView p4 = findViewById(R.id.playerLayoutProgreso4);
        ImageView p5 = findViewById(R.id.playerLayoutProgreso5);

        setImagenProgreso(p1,0);
        setImagenProgreso(p2,1);
        setImagenProgreso(p3,2);
        setImagenProgreso(p4,3);
        setImagenProgreso(p5,4);
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
        if (!SeguridadController.getInstance(getApplicationContext()).checkAutenticado());
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

    protected void start(int nueva_cancion) {
        stop();

        if (canciones != null && modo == MODO.INTERVENCION) {
            if (canciones.length > 0 && nueva_cancion < canciones.length) {
                cancion_actual = nueva_cancion;
                CancionesController.Cancion c = CancionesController.getInstance(this.getApplicationContext()).obtenerCancionId(canciones[cancion_actual]);
                if (c != null) {
                    textViewTitulo.setText(c.titulo);
                    if (c.autor.trim().length() > 0) {
                        textViewAutor.setVisibility(View.VISIBLE);
                        textViewAutor.setText(c.autor);
                    } else {
                        textViewAutor.setVisibility(View.INVISIBLE);
                    }
                    if (c.interprete.trim().length() > 0 && !c.interprete.trim().equals(c.autor.trim())) {
                        textViewInterprete.setVisibility(View.VISIBLE);
                        textViewInterprete.setText(c.interprete);
                    } else {
                        textViewInterprete.setVisibility(View.INVISIBLE);
                    }
                    textViewLetra.setText(c.letra);
                    setFavorito(imageViewFav, c.getEsFavorita());

                    player = MediaPlayer.create(PlayerActivity.this, c.resourceId);
                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            buttonNext.performClick();
                        }
                    });
                    buttonPlayPause.setBackgroundResource(R.drawable.ic_pause_control);
                    textViewTiempo.setText(displayTime(player.getDuration()));

                    player.start();
                    RegistroAccionesController.getInstance(getApplicationContext()).crearRegistroInicioCancion(c.titulo, c.id);
                }
            }
        }

        updateProgreso();
    }

    public String displayTime(int duracion) {
        String time = "";

        int min = duracion / 1000 / 60;
        int secs = duracion / 1000 % 60;

        time = min + ":";
        if (secs < 10) {
            time += "0";
        }
        time += secs;

        return time;
    }

    protected void stop() {
        if (player != null) {
            buttonPlayPause.setBackgroundResource(R.drawable.ic_play_control);
            if (player.isPlaying()) player.stop();
            player.release();

            CancionesController.Cancion c = CancionesController.getInstance(this.getApplicationContext()).obtenerCancionId(canciones[cancion_actual]);
            RegistroAccionesController.getInstance(getApplicationContext()).crearRegistroFinCancion(c.titulo, c.id);
        }
    }

    protected void play() {
        if (player != null) if (!player.isPlaying()) {
            textViewTiempo.setText(displayTime(player.getDuration()-player.getCurrentPosition()));
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