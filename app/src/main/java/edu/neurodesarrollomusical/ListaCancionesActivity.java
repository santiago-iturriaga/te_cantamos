package edu.neurodesarrollomusical;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import edu.neurodesarrollomusical.controller.CancionesController;

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

        int modoOrd = getIntent().getIntExtra(EXTRA_MODO, MODO.LISTAR_CANCIONES.ordinal());
        modo = MODO.values()[modoOrd];

        ListaCancionesAdapter adapter = new ListaCancionesAdapter(this, canciones, modo);
        setListAdapter(adapter);

        Button listoButton = findViewById(R.id.listaCancionesButtonListo);
        listoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                ListaCancionesActivity listaCanciones = (ListaCancionesActivity) v.getContext();
                ListAdapter adapter = listaCanciones.getListAdapter();
                data.putExtra(MainActivity.EXTRA_CANCIONES, ((ListaCancionesAdapter)adapter).getSeleccionadasId());
                setResult(RESULT_OK,data);
                finish();
            }
        });
        switch (modo) {
            case LISTAR_CANCIONES:
                listoButton.setVisibility(View.INVISIBLE);
                break;
            case ELEGIR_CANCIONES_PARA_INTERVENCION:
                listoButton.setVisibility(View.VISIBLE);
                break;
            case ELEGIR_UNA_CANCION_PARA_INTERVENCION:
                listoButton.setVisibility(View.INVISIBLE);
                break;
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
        ((BaseAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        switch (modo) {
            case ELEGIR_UNA_CANCION_PARA_INTERVENCION:
                int[] elegida = {canciones[position].id};
                Intent data = new Intent();
                data.putExtra(MainActivity.EXTRA_CANCIONES, elegida);
                setResult(RESULT_OK,data);
                finish();
                break;
            case ELEGIR_CANCIONES_PARA_INTERVENCION:

                break;
            case LISTAR_CANCIONES:
                int[] elegidas = {canciones[position].id};
                Intent i = new Intent(ListaCancionesActivity.this, PlayerActivity.class);
                i.putExtra(PlayerActivity.EXTRA_MODO, PlayerActivity.MODO.SOLO_LETRA.ordinal());
                i.putExtra(PlayerActivity.EXTRA_CANCIONES, elegidas);
                startActivity(i);
                break;
        }
    }
}