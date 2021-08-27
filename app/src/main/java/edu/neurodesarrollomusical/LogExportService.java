package edu.neurodesarrollomusical;

import android.app.Service;
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
                getApplicationContext();
                ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                boolean isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

                if (isConnected) {
                    RegistroAccionesController.getInstance().enviarLog(getApplicationContext());
                    handler.postDelayed(this, 3600000); // 60 minutos
                } else {
                    if (RegistroAccionesController.getInstance().hayLog(getApplicationContext())) {
                        handler.postDelayed(this, 600000); // 10 minutos
                    }
                }
            } catch (Exception ex) {
                handler.postDelayed(this, 1800000); // 30 minutos
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
            handler.postDelayed(runnable, 5000);
        }

        return START_STICKY;
    }
}