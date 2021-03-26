package com.gha.tp2hernandez;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServicioMensajes extends Service {
    final class MiThread implements Runnable{
        int serviceId;
        MiThread(int serviceId){
            this.serviceId = serviceId;
        }

        @Override
        public void run() {
            while(true){
                Log.d("test", "Mostrar aqui los 5 mensajes");

                Uri mensajes = Uri.parse(Telephony.Sms.CONTENT_URI.toString());
                Cursor cursor = getContentResolver().query(mensajes, null,
                        null, null, null);

                String fecha = null;
                String id = null;
                String contenido = null;
                int flag = 0;


                if (cursor.getCount() > 0) {

                    while (cursor.moveToNext() && flag < 5) {

                        id = cursor.getString(cursor.getColumnIndex(Telephony.Sms._ID));
                        fecha = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));
                        contenido = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));

                        Log.d("test", id);
                        Log.d("test", fecha);
                        Log.d("test", contenido);
                        flag++;
                    }

                }

                try {
                    Thread.sleep(9000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new MiThread(startId));
        thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
