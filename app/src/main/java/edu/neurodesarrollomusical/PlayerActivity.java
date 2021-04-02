package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

        Bundle extras = getIntent().getExtras();
        int[] canciones = extras.getIntArray("CANCIONES");

        showText(Arrays.toString(canciones));
    }
}