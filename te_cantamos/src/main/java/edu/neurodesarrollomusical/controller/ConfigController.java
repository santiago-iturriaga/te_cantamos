package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.content.SharedPreferences;
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

    public boolean getKaraoke(Context context) {
        if (getSharedPreferences(context).contains(context.getResources().getString(R.string.configFile_karaokeKey)))
        {
            return getSharedPreferences(context).getBoolean(context.getResources().getString(R.string.configFile_karaokeKey), false);
        } else
            return false;
    }

    public void setKaraoke(Context context, boolean value) {
        SharedPreferences.Editor myEdit = getSharedPreferences(context).edit();
        myEdit.putBoolean(context.getResources().getString(R.string.configFile_karaokeKey), value);
        myEdit.apply();
    }
}
