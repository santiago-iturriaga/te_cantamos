package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.neurodesarrollomusical.controller.RegistroAccionesController;
import edu.neurodesarrollomusical.controller.SeguridadController;
import edu.neurodesarrollomusical.db.RegistroAccionEntity;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        Button logoutButton = findViewById(R.id.debugButtonLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeguridadController.getInstance(getApplicationContext()).setAutenticado(false);
                finish();
            }
        });
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