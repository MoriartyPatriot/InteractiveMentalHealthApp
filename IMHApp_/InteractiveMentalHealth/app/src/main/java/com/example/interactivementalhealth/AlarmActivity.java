package com.example.interactivementalhealth;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class AlarmActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AlarmPrefs";
    private static final String ALARM_LIST_KEY = "AlarmList";

    private LinearLayout alarmContainer;
    private List<String> alarmList;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        alarmContainer = findViewById(R.id.alarmContainer);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        gson = new Gson();


        alarmList = loadAlarms();
        if (alarmList == null) {
            alarmList = new ArrayList<>();
        }

        ImageView btnBack = findViewById(R.id.imageButtonbtnbak);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa al menú principal (implementa tu lógica aquí)
                finish();
            }
        });

        Button btnAddAlarm = findViewById(R.id.btnAddAlarm);
        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });


        updateAlarmList();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format("%02d:%02d", hourOfDay, minute);
                showAddEditAlarmDialog("Nueva medicación " + time, -1);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void showAddEditAlarmDialog(String alarm, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_edit_alarm, null);
        builder.setView(dialogView);

        final EditText etAlarmName = dialogView.findViewById(R.id.etAlarmName);
        etAlarmName.setText(alarm);

        builder.setTitle(position == -1 ? "Agregar Recordatorio" : "Editar Recordatorio")
                .setPositiveButton(position == -1 ? "Agregar" : "Guardar", (dialog, which) -> {
                    String alarmName = etAlarmName.getText().toString().trim();
                    if (!alarmName.isEmpty()) {
                        if (position == -1) {
                            addAlarm(alarmName);
                        } else {
                            updateAlarm(alarmName, position);
                        }
                    } else {
                        Toast.makeText(AlarmActivity.this, "Por favor, ingrese un nombre para el recordatorio.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel())
                .create()
                .show();
    }

    private void addAlarm(String alarm) {
        alarmList.add(alarm);
        saveAlarms();
        updateAlarmList();
    }

    private void updateAlarm(String alarm, int position) {
        alarmList.set(position, alarm);
        saveAlarms();
        updateAlarmList();
    }

    private void updateAlarmList() {
        alarmContainer.removeAllViews();
        for (int i = 0; i < alarmList.size(); i++) {
            String alarm = alarmList.get(i);
            LinearLayout alarmEntry = new LinearLayout(this);
            alarmEntry.setOrientation(LinearLayout.HORIZONTAL);

            TextView alarmText = new TextView(this);
            alarmText.setText(alarm);
            alarmText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            int finalI = i;
            alarmText.setOnClickListener(v -> showAddEditAlarmDialog(alarm, finalI));

            Switch alarmSwitch = new Switch(this);

            alarmEntry.addView(alarmText);
            alarmEntry.addView(alarmSwitch);

            alarmContainer.addView(alarmEntry);
        }
    }

    private void saveAlarms() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(alarmList);
        editor.putString(ALARM_LIST_KEY, json);
        editor.apply();
    }

    private List<String> loadAlarms() {
        String json = sharedPreferences.getString(ALARM_LIST_KEY, null);
        if (json != null) {
            Type type = new TypeToken<List<String>>() {}.getType();
            return gson.fromJson(json, type);
        }
        return null;
    }
}



