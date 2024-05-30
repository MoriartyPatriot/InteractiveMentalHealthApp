package com.example.interactivementalhealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DiagnosisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);

        TextView textViewDiagnosis = findViewById(R.id.textViewDiagnosis);

        Intent intent = getIntent();
        String diagnosis = intent.getStringExtra("DIAGNOSIS");
        textViewDiagnosis.setText(diagnosis);

        ImageView buttonImage = findViewById(R.id.imageButton);
        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
