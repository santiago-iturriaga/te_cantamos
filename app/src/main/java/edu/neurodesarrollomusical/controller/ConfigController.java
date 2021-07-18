package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

import edu.neurodesarrollomusical.R;

import static android.content.Context.MODE_PRIVATE;

public class ConfigController {
    private static ConfigController _instance;

    public synchronized static ConfigController getInstance() {
        if (_instance == null) {
            _instance = new ConfigController();
        }
        return _instance;
    }

    private synchronized SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getResources().getString(R.string.configFile),MODE_PRIVATE);
    }

    private ConfigController() {   }

    public boolean getAutenticado(Context context) {
        if (getSharedPreferences(context).contains(context.getResources().getString(R.string.configFile_autenticadoKey)))
        {
            return getSharedPreferences(context).getBoolean(context.getResources().getString(R.string.configFile_autenticadoKey), false);
        } else
            return false;
    }

    public String getUsuario(Context context) {
        if (getSharedPreferences(context).contains(context.getResources().getString(R.string.configFile_usuarioKey)))
        {
            return getSharedPreferences(context).getString(context.getResources().getString(R.string.configFile_usuarioKey), null);
        } else
            return null;
    }

    public synchronized void setAutenticado(Context context, String usuario) {
        SharedPreferences.Editor myEdit = getSharedPreferences(context).edit();
        myEdit.putBoolean(context.getResources().getString(R.string.configFile_autenticadoKey), true);
        myEdit.putString(context.getResources().getString(R.string.configFile_usuarioKey), usuario);
        myEdit.commit();
    }

    public synchronized void unsetAutenticado(Context context) {
        SharedPreferences.Editor myEdit = getSharedPreferences(context).edit();
        myEdit.putBoolean(context.getResources().getString(R.string.configFile_autenticadoKey), false);
        myEdit.remove(context.getResources().getString(R.string.configFile_usuarioKey));
        myEdit.commit();
    }
}
