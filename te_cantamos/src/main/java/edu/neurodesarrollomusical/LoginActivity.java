package edu.neurodesarrollomusical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import edu.neurodesarrollomusical.controller.SeguridadController;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SeguridadController.getInstance().getAutenticado(getApplicationContext())) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        Button btnLoginAceptar = findViewById(R.id.loginButtonAceptar);
        btnLoginAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText user = findViewById(R.id.txtLoginUser);
                TextInputEditText pass = findViewById(R.id.txtLoginPassword);

                String userTxt = user.getText().toString();
                String passTxt = pass.getText().toString();

                if (SeguridadController.getInstance().login(getApplicationContext(), userTxt, passTxt)) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    MensajesHelper.showText(v.getContext(), "Usuario o contraseña incorrecta.");
                }
            }
        });
    }
}