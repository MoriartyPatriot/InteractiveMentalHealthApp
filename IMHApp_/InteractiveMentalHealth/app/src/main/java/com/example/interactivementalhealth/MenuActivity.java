package com.example.interactivementalhealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Verificar si el usuario ha iniciado sesión
        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            // Si el usuario no ha iniciado sesión, redirigir al LogInActivity
            Intent intent = new Intent(MenuActivity.this, LogInActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual para que no pueda regresar a ella
            return; // Salir del método onCreate para evitar la ejecución del resto del código
        }

        ImageView buttonCerrar = findViewById(R.id.imageViewLogOut);
        Button testBtn = findViewById(R.id.btnTest);
        Button controlBtn = findViewById(R.id.btnControl);
        Button sintomatologiaBtn = findViewById(R.id.btnSintomatologia);

        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión y redirigir al LogInActivity
                logout();
            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });

        controlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AlarmActivity.class);
                startActivity(intent);
            }
        });

        sintomatologiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SymptomatologyActivity.class);
                startActivity(intent);
            }
        });
    }

    private void logout() {
        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        Intent intent = new Intent(MenuActivity.this, LogInActivity.class);
        startActivity(intent);
        finish(); // Finalizar la actividad actual
    }
}

