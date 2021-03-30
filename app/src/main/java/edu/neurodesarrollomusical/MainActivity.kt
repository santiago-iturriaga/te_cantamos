package edu.neurodesarrollomusical

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAyuda = findViewById<Button>(R.id.buttonAyuda)
        buttonAyuda.setOnClickListener {
            Toast.makeText(this@MainActivity, "Ayuda!", Toast.LENGTH_SHORT).show()
        }

        val buttonListaCanciones = findViewById<Button>(R.id.buttonListaCanciones)
        buttonListaCanciones.setOnClickListener {
            Toast.makeText(this@MainActivity, "Lista de canciones!", Toast.LENGTH_SHORT).show()
        }

        val buttonPlayAleatorio = findViewById<Button>(R.id.buttonPlayAleatorio)
        buttonPlayAleatorio.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java).apply {
                putExtra("MODO", 0)
            }
            startActivity(intent)
        }

        val buttonPlayElegidas = findViewById<Button>(R.id.buttonPlayElegidas)
        buttonPlayElegidas.setOnClickListener {
            Toast.makeText(this@MainActivity, "Escuchas elegidas!", Toast.LENGTH_SHORT).show()
        }

        val buttonPlayFavoritas = findViewById<Button>(R.id.buttonPlayFavoritos)
        buttonPlayFavoritas.setOnClickListener {
            Toast.makeText(this@MainActivity, "Escuchas favoritas!", Toast.LENGTH_SHORT).show()
        }

        val buttonPlaySoloUna = findViewById<Button>(R.id.buttonPlaySoloUna)
        buttonPlaySoloUna.setOnClickListener {
            Toast.makeText(this@MainActivity, "Escuchas solo una!", Toast.LENGTH_SHORT).show()
        }
    }
}