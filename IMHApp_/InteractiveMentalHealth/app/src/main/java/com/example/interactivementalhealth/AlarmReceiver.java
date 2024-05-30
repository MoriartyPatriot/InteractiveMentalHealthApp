package com.example.interactivementalhealth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Es hora de tomar tu medicación", Toast.LENGTH_LONG).show();
        // Aquí podrías agregar la lógica para mostrar una notificación, por ejemplo.
    }
}
