package edu.neurodesarrollomusical

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAyuda = findViewById<Button>(R.id.buttonAyuda)
        buttonAyuda.setOnClickListener {  }

        val buttonListaCanciones = findViewById<Button>(R.id.buttonListaCanciones)
        buttonListaCanciones.setOnClickListener { }

        val buttonPlayAleatorio = findViewById<Button>(R.id.buttonPlayAleatorio)
        buttonPlayAleatorio.setOnClickListener { }

        val buttonPlayElegidas = findViewById<Button>(R.id.buttonPlayElegidas)
        buttonPlayElegidas.setOnClickListener { }

        val buttonPlayFavoritas = findViewById<Button>(R.id.buttonPlayFavoritos)
        buttonPlayFavoritas.setOnClickListener { }

        val buttonPlaySoloUna = findViewById<Button>(R.id.buttonPlaySoloUna)
        buttonPlaySoloUna.setOnClickListener { }
    }
}