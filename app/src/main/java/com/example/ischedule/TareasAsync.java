package com.example.ischedule;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class TareasAsync extends AsyncTask<Void, Void, String> {

    private WeakReference<TextView> mensaje;
    TareasAsync(TextView msg) {
        mensaje = new WeakReference<>(msg);
    }

    @Override
    protected String doInBackground(Void... voids) {
        int s = 5000;
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Haz tu Evento favorito!!!";
    }

    //protected void onPostExecute(String result) {
//        mensaje.get().setText(result);
//    }
}
