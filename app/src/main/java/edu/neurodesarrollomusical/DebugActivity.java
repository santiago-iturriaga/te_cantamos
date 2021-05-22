package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.neurodesarrollomusical.controller.RegistroAccionesController;
import edu.neurodesarrollomusical.db.RegistroAccionEntity;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<RegistroAccionEntity> reg = RegistroAccionesController.getInstance(getApplicationContext()).obtenerBatchRegistro();
        TextView textView = findViewById(R.id.debugTextView);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < reg.size(); i++) {
            RegistroAccionEntity aux;
            aux = reg.get(i);
            texto.append("[" + aux.id + "] ");
            texto.append(aux.numeroCancion);
            texto.append(":");
            texto.append(aux.nombreCancion);
            texto.append(" ");
            texto.append(simpleDateFormat.format(aux.fechaAccion));
            texto.append(" ");
            if (aux.accionInicio) {
                texto.append(" <INICIO>");
            } else if (aux.accionFin) {
                texto.append(" <FIN>");
            }
            texto.append("\n");
        }
        textView.setText(texto.toString());
    }
}