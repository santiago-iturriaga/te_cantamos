package edu.neurodesarrollomusical;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MensajesHelper {
    static public void showText(Context c, String text) {
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        Toast toast = Toast.makeText(c, text, Toast.LENGTH_SHORT);
        ViewGroup group = (ViewGroup) toast.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(20);
        group.setBackgroundColor(0x222222);

        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.WHITE);
        //v.setBackgroundColor(0x222222);
        v.setPadding(10,10,10,10);
        toast.show();
    }
}
