package edu.neurodesarrollomusical.controller;

import android.content.Context;
import android.provider.Settings;
import android.widget.TextView;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.text.SimpleDateFormat;
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

    private RegistroAccionesController() {    }

    public void crearRegistroInicioCancion(Context context, String cancionTitulo, int cancionNumero) {
        AppDatabaseController.getInstance(context).getDB().registroAccionDAO().insert(
                new RegistroAccionEntity(new Date().getTime(), cancionTitulo, cancionNumero, true, false));
    }

    public void crearRegistroFinCancion(Context context, String cancionTitulo, int cancionNumero) {
        AppDatabaseController.getInstance(context).getDB().registroAccionDAO().insert(
                new RegistroAccionEntity(new Date().getTime(), cancionTitulo, cancionNumero, false, true));
    }

    public List<RegistroAccionEntity> obtenerBatchRegistro(Context context) {
        return AppDatabaseController.getInstance(context).getDB().registroAccionDAO().getAllBatch();
    }

    void postJson(String url, String json) {
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
                String s = e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    // Do what you want to do with the response.
                } else {
                    // Request not successful
                }
            }
        });
        //.execute();
        //return response.body().string();
    }

    String buildJson(Context context) {
        List<RegistroAccionEntity> reg = obtenerBatchRegistro(context);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public void enviarLog(Context context) {
        String json = buildJson(context);
        postJson("http://192.168.50.242:8008/app", json);
    }
}

