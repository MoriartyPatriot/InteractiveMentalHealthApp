package com.example.interactivementalhealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        ImageView backButton = findViewById(R.id.imageViewBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String savedEmail = sharedPref.getString("Email", null);
                String savedPassword = sharedPref.getString("Password", null);

                if (email.equals(savedEmail) && password.equals(savedPassword)) {
                    Toast.makeText(LogInActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    // Guardar la sesión iniciada
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Intent intent = new Intent(LogInActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish(); // Finalizar LogInActivity para que no pueda regresar a esta actividad
                } else {
                    Toast.makeText(LogInActivity.this, "Email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

