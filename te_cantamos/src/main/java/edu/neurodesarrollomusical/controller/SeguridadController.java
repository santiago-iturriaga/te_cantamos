package edu.neurodesarrollomusical.controller;

import android.content.Context;

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
        return true;
    }

    public boolean login(Context context, String usuario, String contrasenia) {
        return true;
    }

    public void logout(Context context) {

    }

    public boolean getAutenticado(Context context) {
        return true;
    }

    public String getUsuario(Context context) {
        return "";
    }
}
