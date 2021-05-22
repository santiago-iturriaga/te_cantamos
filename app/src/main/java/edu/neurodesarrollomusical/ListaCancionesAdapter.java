package edu.neurodesarrollomusical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import edu.neurodesarrollomusical.controller.CancionesController;

public class ListaCancionesAdapter extends ArrayAdapter<CancionesController.Cancion> {
    ListaCancionesActivity.MODO modo;
    CancionesController.Cancion[] canciones;
    ArrayList<Integer> seleccionadas;
    int max;

    Context context;

    public ListaCancionesAdapter(@NonNull Context context, @NonNull CancionesController.Cancion[] canciones, ListaCancionesActivity.MODO modo) {
        super(context, -1, canciones);

        max = context.getResources().getInteger(R.integer.intervencion_max_canciones);

        this.context = context;
        this.canciones = canciones;
        this.modo = modo;
        this.seleccionadas = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_lista_canciones_row, parent, false);
        rowView.setTag(canciones[position]);

        TextView textView;
        textView = (TextView) rowView.findViewById(R.id.listaCancionesRowTitulo);
        textView.setText(canciones[position].titulo);
        textView = (TextView) rowView.findViewById(R.id.listaCancionesRowAutor);
        if (canciones[position].autor.trim().length() > 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(canciones[position].autor);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }
        textView = (TextView) rowView.findViewById(R.id.listaCancionesRowInterprete);
        textView.setText(canciones[position].interprete);
        if (canciones[position].interprete.trim().length() > 0 && !canciones[position].interprete.trim().equals(canciones[position].autor)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(canciones[position].interprete);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }

        ImageView imageViewFav = (ImageView) rowView.findViewById(R.id.listaCancionesRowFavorita);
        ImageView imageViewSel = (ImageView) rowView.findViewById(R.id.listaCancionesRowSeleccionado);

        if (modo == ListaCancionesActivity.MODO.ELEGIR_CANCIONES_PARA_INTERVENCION) {
            imageViewFav.setVisibility(View.INVISIBLE);
            imageViewSel.setVisibility(View.VISIBLE);
            setSeleccionado(imageViewSel, seleccionadas.contains(position));

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CancionesController.Cancion c = (CancionesController.Cancion)v.getTag();
                    ImageView click_sel = (ImageView) v.findViewById(R.id.listaCancionesRowSeleccionado);

                    if (seleccionadas.contains(position)) {
                        setSeleccionado(click_sel,false);
                        seleccionadas.remove(new Integer(position));
                    } else {
                        if (seleccionadas.size() < max) {
                            setSeleccionado(click_sel, true);
                            seleccionadas.add(position);
                        } else {
                            MensajesHelper.showText(context, "Máxima cantidad de canciones seleccionadas");
                        }
                    }
                }
            });
        } else {
            imageViewFav.setVisibility(View.VISIBLE);
            imageViewSel.setVisibility(View.INVISIBLE);
            setFavorito(imageViewFav, canciones[position].es_favorita);
        }

        return rowView;
    }

    public int[] getSeleccionadasId() {
        int[] elegidas = new int[seleccionadas.size()];
        for (int i=0; i < seleccionadas.size(); i++)
        {
            elegidas[i] = canciones[seleccionadas.get(i).intValue()].id;
        }
        return elegidas;
    }

    public void setFavorito(ImageView fav, boolean estado) {
        if (estado) {
            fav.setVisibility(View.VISIBLE);
            fav.setImageResource(R.drawable.ic_cancion_favorita);
        } else {
            fav.setImageResource(R.drawable.ic_cancion_no_favorita);
            fav.setVisibility(View.INVISIBLE);
        }
    }

    /*
    public void toggleSeleccionado(ImageView sel) {
        if (sel.getVisibility() == View.INVISIBLE) {
            sel.setVisibility(View.VISIBLE);
        } else {
            sel.setVisibility(View.INVISIBLE);
        }
    }
     */

    public void setSeleccionado(ImageView sel, boolean estado) {
        if (estado) {
            sel.setImageResource(R.drawable.ic_lista_canciones_sel);
        } else {
            sel.setImageResource(R.drawable.ic_lista_canciones_unsel);
        }
    }
}
