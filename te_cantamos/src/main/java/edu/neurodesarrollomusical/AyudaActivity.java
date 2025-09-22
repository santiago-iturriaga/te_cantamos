package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class AyudaActivity extends AppCompatActivity {
    MediaPlayer player;
    ImageButton buttonPlayPause, buttonPrev;
    TextView textViewTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        player = MediaPlayer.create(AyudaActivity.this, R.raw.ayuda);

        TextView ayudaTextView = findViewById(R.id.ayudaTextView);
        textViewTiempo = findViewById(R.id.ayudaTextViewTiempo);
        textViewTiempo.setText(displayTime(player.getDuration()-player.getCurrentPosition()));

        buttonPlayPause = findViewById(R.id.ayudaImageViewPlayPauseControl);
        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    if (player.isPlaying()) pause();
                    else play();
                }
            }
        });

        buttonPrev = findViewById(R.id.ayudaImageViewPrevControl);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restart();
            }
        });
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

    protected void restart() {
        stop();
        play();
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
            if (player.isPlaying()) {
                player.stop();
                try {
                    player.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void play() {
        if (player != null) if (!player.isPlaying()) {
            textViewTiempo.setText(displayTime(player.getDuration()-player.getCurrentPosition()));
            player.start();
            buttonPlayPause.setBackgroundResource(R.drawable.ic_pause_control);

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
        }
    }

    protected void pause() {
        if (player != null) if (player.isPlaying()) {
            player.pause();
            buttonPlayPause.setBackgroundResource(R.drawable.ic_play_control);
        }
    }
}