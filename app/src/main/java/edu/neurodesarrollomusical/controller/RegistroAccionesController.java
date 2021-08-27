package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.neurodesarrollomusical.R;
import edu.neurodesarrollomusical.db.RegistroAccionEntity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.*;

public class RegistroAccionesController {
    private static RegistroAccionesController _instance;

    public synchronized static RegistroAccionesController getInstance() {
        if (_instance == null) {
            _instance = new RegistroAccionesController();
        }
        return _instance;
    }

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private RegistroAccionesController() {
    }

    public void crearRegistroInicioCancion(Context context, String cancionTitulo, int cancionNumero) {
        AppDatabaseController.getInstance(context).getDB().registroAccionDAO().insert(
                new RegistroAccionEntity(new Date().getTime(), cancionTitulo, cancionNumero, true, false));
    }

    public void crearRegistroFinCancion(Context context, String cancionTitulo, int cancionNumero) {
        AppDatabaseController.getInstance(context).getDB().registroAccionDAO().insert(
                new RegistroAccionEntity(new Date().getTime(), cancionTitulo, cancionNumero, false, true));
    }

    public List<RegistroAccionEntity> obtenerOneRegistro(Context context) {
        return AppDatabaseController.getInstance(context).getDB().registroAccionDAO().getOne();
    }

    public List<RegistroAccionEntity> obtenerBatchRegistro(Context context) {
        return AppDatabaseController.getInstance(context).getDB().registroAccionDAO().getAllBatch();
    }

    boolean postJson(Context context, List<RegistroAccionEntity> reg, String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Something went wrong
                // String s = e.getMessage();
                Log.e(this.getClass().toString(), e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // String responseStr = response.body().string();
                    // Do what you want to do with the response.
                    Log.i(this.getClass().toString(), response.message());
                    String json = response.body().string();

                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(json);
                        boolean success = obj.getBoolean("success");
                        boolean logout = obj.getBoolean("logout");

                        if (success) {
                            try {
                                List<Long> ids = new ArrayList<Long>();
                                for (int i = 0; i < reg.size(); i++) {
                                    ids.add(Long.valueOf(reg.get(i).id));
                                }
                                AppDatabaseController.getInstance(context).getDB().registroAccionDAO().deleteItemByIds(ids);
                            } catch (Exception ex) {
                                Log.e(this.getClass().toString(), ex.getMessage());
                            }

                            if (logout) {
                                SeguridadController.getInstance().logout(context);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Request not successful
                    Log.e(this.getClass().toString(), response.message());
                }
            }
        });
        return true;
    }

    String buildJson(Context context, List<RegistroAccionEntity> reg) {
        String usuario = SeguridadController.getInstance().getUsuario(context);
        if (usuario == null) {
            usuario = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<RegistroAccionEntity> jsonAdapter = moshi.adapter(RegistroAccionEntity.class);

        StringBuilder texto = new StringBuilder();
        texto.append("{\n\"usuario\": \"");
        texto.append(usuario);
        texto.append("\"");
        if (reg.size() > 0) {
            texto.append(",\n");
            texto.append("\"log\": [\n");
            texto.append(jsonAdapter.toJson(reg.get(0)));
            for (int i = 1; i < reg.size(); i++) {
                texto.append(",\n");
                texto.append(jsonAdapter.toJson(reg.get(i)));
            }
            texto.append("]\n");
        }
        texto.append("\n}");
        return texto.toString();
    }

    public boolean hayLog(Context context) {
        return (obtenerOneRegistro(context).size() > 0);
    }

    public boolean enviarLog(Context context) throws IOException {
        List<RegistroAccionEntity> reg = obtenerBatchRegistro(context);
        if (reg.size() > 0) {
            String json = buildJson(context, reg);
            return postJson(context, reg, context.getResources().getString(R.string.server_IP), json);
        } else
            return true;
    }
}

