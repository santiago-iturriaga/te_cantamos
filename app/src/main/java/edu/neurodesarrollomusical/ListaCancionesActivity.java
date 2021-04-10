package edu.neurodesarrollomusical;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ListaCancionesActivity extends ListActivity {
    static final public String EXTRA_MODO = "MODO";

    public enum MODO {
        LISTAR_CANCIONES,
        ELEGIR_CANCIONES_PARA_INTERVENCION,
        ELEGIR_UNA_CANCION_PARA_INTERVENCION
    }

    ListView listaCancionesListView;
    CancionesController.Cancion[] canciones;
    MODO modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);

        canciones = CancionesController.getInstance(this).listarCanciones();

        ListaCancionesAdapter adapter = new ListaCancionesAdapter(this, canciones);
        setListAdapter(adapter);

        int modoOrd = getIntent().getIntExtra(EXTRA_MODO, MODO.LISTAR_CANCIONES.ordinal());
        modo = MODO.values()[modoOrd];
        switch (modo) {
            case LISTAR_CANCIONES:
                MensajesHelper.showText(this, "ListaCanciones::MODO::LISTAR_CANCIONES");
                break;
            case ELEGIR_CANCIONES_PARA_INTERVENCION:
                MensajesHelper.showText(this, "ListaCanciones::MODO::ELEGIR_CANCIONES_PARA_INTERVENCION");
                break;
            case ELEGIR_UNA_CANCION_PARA_INTERVENCION:
                MensajesHelper.showText(this, "ListaCanciones::MODO::ELEGIR_UNA_CANCION_PARA_INTERVENCION");
                break;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (modo) {
            case ELEGIR_UNA_CANCION_PARA_INTERVENCION:
                int[] elegida = {position};
                Intent data = new Intent();
                data.putExtra(MainActivity.EXTRA_CANCIONES, elegida);
                setResult(RESULT_OK,data);
                finish();
            default:
                MensajesHelper.showText(this, canciones[position].titulo + " [NADA]");
        }
    }
}