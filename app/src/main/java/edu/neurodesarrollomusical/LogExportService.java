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
                ConnectivityManager cm = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                boolean isConnected = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

                if (isConnected) {
                    RegistroAccionesController.getInstance().enviarLog(getApplicationContext());
                }
            } catch (Exception ex) {
                Log.e(this.getClass().toString(), ex.getMessage());
            }
            handler.postDelayed(this, 15000);
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