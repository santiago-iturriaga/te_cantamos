package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.content.Intent;

import org.jetbrains.annotations.NotNull;

import edu.neurodesarrollomusical.LoginActivity;

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

    public void setAutenticado() {
        _autenticado = true;
        ConfigController.getInstance(_context).setAutenticado();
    }
}
