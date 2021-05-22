package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.neurodesarrollomusical.controller.SeguridadController;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLoginAceptar = findViewById(R.id.loginButtonAceptar);
        btnLoginAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = findViewById(R.id.txtLoginUser);
                EditText pass = findViewById(R.id.txtLoginPassword);

                String userTxt = user.getText().toString();
                String passTxt = pass.getText().toString();

                if (userTxt.equals("pepe") && passTxt.equals("coco")) {
                    SeguridadController.getInstance(getApplicationContext()).setAutenticado();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                } else {
                    MensajesHelper.showText(v.getContext(), "Usuario o contraseña incorrecta.");
                }
            }
        });
    }
}