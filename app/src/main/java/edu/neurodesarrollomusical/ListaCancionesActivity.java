package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ListaCancionesActivity extends AppCompatActivity {
    public enum MODO {
        LISTAR_CANCIONES,
        ELEGIR_CANCIONES_PARA_INTERVENCION,
        ELEGIR_UNA_CANCION_PARA_INTERVENCION
    }

    private void showText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);

        int modoOrd = getIntent().getIntExtra("MODO", MODO.LISTAR_CANCIONES.ordinal());
        switch (MODO.values()[modoOrd]) {
            case LISTAR_CANCIONES:
                showText("ListaCanciones::MODO::LISTAR_CANCIONES");
                break;
            case ELEGIR_CANCIONES_PARA_INTERVENCION:
                showText("ListaCanciones::MODO::ELEGIR_CANCIONES_PARA_INTERVENCION");
                break;
            case ELEGIR_UNA_CANCION_PARA_INTERVENCION:
                showText("ListaCanciones::MODO::ELEGIR_UNA_CANCION_PARA_INTERVENCION");
                break;
        }

        /*
        Intent data = new Intent();
        data.putExtra("streetkey","streetname");
        data.putExtra("citykey","cityname");
        data.putExtra("homekey","homename");
        setResult(RESULT_OK,data);
        finish();
         */
    }
}