package edu.neurodesarrollomusical.controller;

import android.content.Context;

public class RegistroAccionesController {
    private static RegistroAccionesController _instance;

    public synchronized static RegistroAccionesController getInstance(Context context) {
        if (_instance == null) {
            _instance = new RegistroAccionesController(context);
        }
        return _instance;
    }

    private RegistroAccionesController(Context context) {

    }
}
