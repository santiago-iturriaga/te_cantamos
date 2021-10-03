package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.content.Intent;

import org.jetbrains.annotations.NotNull;

import edu.neurodesarrollomusical.LoginActivity;
import edu.neurodesarrollomusical.R;

import android.provider.Settings.Secure;

public class SeguridadController {
    private static SeguridadController _instance;

    public synchronized static SeguridadController getInstance() {
        if (_instance == null) {
            _instance = new SeguridadController();
        }
        return _instance;
    }

    private SeguridadController() { }

    public boolean checkAutenticado(Context context) {
        if (!ConfigController.getInstance().getAutenticado(context)) {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            return false;
        } else
            return true;
    }

    public boolean login(Context context, String usuario, String contrasenia) {
        if (contrasenia.equals(context.getResources().getString(R.string.app_password))) {
            ConfigController.getInstance().setAutenticado(context, usuario);
            return true;
        } else {
            return false;
        }
    }

    public void logout(Context context) {
        ConfigController.getInstance().unsetAutenticado(context);
    }

    public boolean getAutenticado(Context context) {
        return ConfigController.getInstance().getAutenticado(context);
    }

    public String getUsuario(Context context) {
        return ConfigController.getInstance().getUsuario(context);
    }
}
