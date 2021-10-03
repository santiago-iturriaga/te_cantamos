package edu.neurodesarrollomusical;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import edu.neurodesarrollomusical.controller.RegistroAccionesController;

public class LogExportService extends Service {
    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Context context = getApplicationContext();
                if (RegistroAccionesController.getInstance().hayLog(context)) {
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    boolean isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

                    if (isConnected) {
                        RegistroAccionesController.getInstance().enviarLog(context);
                        handler.postDelayed(this, 3600000); // 60 minutos
                    } else {
                        handler.postDelayed(this, 1800000); // 30 minutos
                    }
                } else {
                    handler.postDelayed(this, 3600000); // 60 minutos
                }
            } catch (Exception ex) {
                handler.postDelayed(this, 3600000); // 60 minutos
                Log.e(this.getClass().toString(), ex.getMessage());
            }
        }
    };

    private boolean hasRunnable;

    public LogExportService() {
        hasRunnable = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!hasRunnable) {
            hasRunnable = true;
            handler.postDelayed(runnable, 10000); // 10 segundos de delay
        }

        return START_STICKY;
    }
}