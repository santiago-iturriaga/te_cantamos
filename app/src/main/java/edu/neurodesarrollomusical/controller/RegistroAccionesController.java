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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistroAccionesController {
    private static RegistroAccionesController _instance;

    public synchronized static RegistroAccionesController getInstance(Context context) {
        if (_instance == null) {
            _instance = new RegistroAccionesController(context);
        }
        return _instance;
    }

    Context _context;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private RegistroAccionesController(Context context) {
        _context = context;
    }

    public void crearRegistroInicioCancion(String cancionTitulo, int cancionNumero) {
        AppDatabaseController.getInstance(_context).getDB().registroAccionDAO().insert(
                new RegistroAccionEntity(new Date(), cancionTitulo, cancionNumero, true, false));
    }

    public void crearRegistroFinCancion(String cancionTitulo, int cancionNumero) {
        AppDatabaseController.getInstance(_context).getDB().registroAccionDAO().insert(
                new RegistroAccionEntity(new Date(), cancionTitulo, cancionNumero, false, true));
    }

    public List<RegistroAccionEntity> obtenerBatchRegistro() {
        return AppDatabaseController.getInstance(_context).getDB().registroAccionDAO().getAllBatch();
    }

    String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String buildJson() {
        List<RegistroAccionEntity> reg = obtenerBatchRegistro();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String usuario = SeguridadController.getInstance(_context).getUsuario();
        if (usuario == null) {
            usuario = Settings.Secure.getString(_context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<RegistroAccionEntity> jsonAdapter = moshi.adapter(RegistroAccionEntity.class);

        StringBuilder texto = new StringBuilder();
        texto.append("\"usuario\":\"");
        texto.append(usuario);
        texto.append("\"");
        for (int i = 0; i < reg.size(); i++) {
            texto.append(",\n");
            texto.append(jsonAdapter.toJson(reg.get(i)));
        }
        return texto.toString();
    }

    public void enviarLog() throws IOException {
        String json = buildJson();
        String response = postJson("http://192.168.50.24:8080/app", json);
    }
}
