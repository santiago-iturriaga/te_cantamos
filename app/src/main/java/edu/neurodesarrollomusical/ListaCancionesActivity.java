package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaCancionesActivity extends ListActivity {
    public enum MODO {
        LISTAR_CANCIONES,
        ELEGIR_CANCIONES_PARA_INTERVENCION,
        ELEGIR_UNA_CANCION_PARA_INTERVENCION
    }

    private void showText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    ListView listaCancionesListView;
    CancionesController.Cancion[] canciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);

        canciones = CancionesController.getInstance(this).listarCanciones();

        ListaCancionesAdapter adapter = new ListaCancionesAdapter(this, canciones);
        setListAdapter(adapter);

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO implement some logic
        //Toast.makeText(this, canciones[position].titulo, Toast.LENGTH_SHORT).show();
    }
}