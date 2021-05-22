package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.neurodesarrollomusical.controller.SeguridadController;

public class AyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SeguridadController.getInstance(getApplicationContext()).checkAutenticado());
    }
}