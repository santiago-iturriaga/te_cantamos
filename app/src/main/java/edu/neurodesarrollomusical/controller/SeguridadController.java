package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.content.Intent;

import org.jetbrains.annotations.NotNull;

import edu.neurodesarrollomusical.LoginActivity;
import android.provider.Settings.Secure;

public class SeguridadController {
    private static SeguridadController _instance;

    public synchronized static SeguridadController getInstance(@NotNull Context context) {
        if (_instance == null) {
            _instance = new SeguridadController(context);
        }
        return _instance;
    }

    boolean _autenticado;
    Context _context;

    private SeguridadController(Context context) {
        _context = context;
        _autenticado = ConfigController.getInstance(context).getAutenticado();
    }

    public boolean checkAutenticado() {
        if (!_autenticado) {
            Intent i = new Intent(_context, LoginActivity.class);
            _context.startActivity(i);
            return false;
        } else
            return true;
    }

    public boolean login(String usuario, String contrasenia) {
        //if (usuario.equals("pepe") && contrasenia.equals("coco")) {
        if (contrasenia.equals("coco")) {
            ConfigController.getInstance(_context).setAutenticado(usuario);
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        ConfigController.getInstance(_context).unsetAutenticado();
    }

    public boolean getAutenticado() {
        return ConfigController.getInstance(_context).getAutenticado();
    }

    public String getUsuario() {
        return ConfigController.getInstance(_context).getUsuario();
    }
}
