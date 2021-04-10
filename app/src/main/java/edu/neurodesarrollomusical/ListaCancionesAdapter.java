package edu.neurodesarrollomusical;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class ListaCancionesAdapter extends ArrayAdapter<CancionesController.Cancion> {
    CancionesController.Cancion[] canciones;
    Context context;

    public ListaCancionesAdapter(@NonNull Context context, @NonNull CancionesController.Cancion[] objects) {
        super(context, -1, objects);

        this.context = context;
        this.canciones = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_lista_canciones_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.listaCancionesRowTitulo);
        textView.setText(canciones[position].titulo);

        textView = (TextView) rowView.findViewById(R.id.listaCancionesRowAutor);
        textView.setText(canciones[position].autor);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.listaCancionesRowFavorita);
        imageView.setVisibility(View.INVISIBLE);

        // change the icon for Windows and iPhone
        /*String s = values[position];
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }*/

        return rowView;
    }


}
