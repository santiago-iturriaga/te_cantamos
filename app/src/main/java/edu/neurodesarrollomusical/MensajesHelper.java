package edu.neurodesarrollomusical;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MensajesHelper {
    static public void showText(Context c, String text) {
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        Toast toast = Toast.makeText(c, text, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.WHITE);
        toast.show();
    }
}
