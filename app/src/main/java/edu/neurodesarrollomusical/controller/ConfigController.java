package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

import edu.neurodesarrollomusical.R;

import static android.content.Context.MODE_PRIVATE;

public class ConfigController {
    private static ConfigController _instance;

    public synchronized static ConfigController getInstance(@NotNull Context context) {
        if (_instance == null) {
            _instance = new ConfigController(context);
        }
        return _instance;
    }

    Context _context;
    SharedPreferences _sharedPreferences;

    private ConfigController(@NotNull Context context) {
        _context = context;
        _sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.configFile),MODE_PRIVATE);
    }

    public boolean getAutenticado() {
        if (_sharedPreferences.contains(_context.getResources().getString(R.string.configFile_autenticadoKey)))
        {
            return _sharedPreferences.getBoolean(_context.getResources().getString(R.string.configFile_autenticadoKey), false);
        } else
            return false;
    }

    public void setAutenticado(boolean autenticado) {
        SharedPreferences.Editor myEdit = _sharedPreferences.edit();
        myEdit.putBoolean(_context.getResources().getString(R.string.configFile_autenticadoKey), autenticado);
        myEdit.commit();
    }
}
