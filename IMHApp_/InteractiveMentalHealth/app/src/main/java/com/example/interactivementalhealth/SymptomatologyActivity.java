package com.example.interactivementalhealth;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SymptomatologyActivity extends AppCompatActivity {

    private LinearLayout symptomContainer;
    private Set<String> symptomSet;
    private String[] symptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptomatology);

        symptomContainer = findViewById(R.id.symptomContainer);
        symptomSet = new HashSet<>();
        symptoms = new String[]{
                "Ansiedad",
                "Pensamientos intrusivos",
                "Tic nerviosos",
                "Falta de concentración",
                "Fatiga",
                "Horarios irregulares de sueño"
        };

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresa al menú principal (implementa tu lógica aquí)
                finish();
            }
        });

        ImageView btnAddSymptom = findViewById(R.id.btnAddSymptom);
        btnAddSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSymptomSelectionDialog();
            }
        });

        Button btnGenerateTips = findViewById(R.id.btnGenerateTips);
        btnGenerateTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateTips();
            }
        });
    }

    private void showSymptomSelectionDialog() {
        boolean[] checkedSymptoms = new boolean[symptoms.length];
        final List<String> selectedSymptoms = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione los síntomas")
                .setMultiChoiceItems(symptoms, checkedSymptoms, (dialog, which, isChecked) -> {
                    if (isChecked) {
                        selectedSymptoms.add(symptoms[which]);
                    } else {
                        selectedSymptoms.remove(symptoms[which]);
                    }
                })
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    for (String symptom : selectedSymptoms) {
                        addSymptom(symptom);
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void addSymptom(String symptom) {
        if (!symptomSet.contains(symptom)) {
            symptomSet.add(symptom);
            TextView symptomTextView = new TextView(this);
            symptomTextView.setText(symptom);
            symptomTextView.setTextSize(18);
            symptomTextView.setPadding(16, 16, 16, 16);
            symptomTextView.setBackgroundResource(R.drawable.symptom_background);

            symptomContainer.addView(symptomTextView);
        }
    }

    private void generateTips() {
        if (symptomSet.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese al menos un síntoma.", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder tips = new StringBuilder("Consejos basados en los síntomas seleccionados:\n\n");

        for (String symptom : symptomSet) {
            switch (symptom) {
                case "Ansiedad":
                    tips.append("Para la ansiedad, intente técnicas de relajación como la respiración profunda y la meditación.\n\n");
                    break;
                case "Pensamientos intrusivos":
                    tips.append("Para los pensamientos intrusivos, practique mindfulness y enfoque en el presente.\n\n");
                    break;
                case "Tic nerviosos":
                    tips.append("Para los tic nerviosos, trate de identificar los desencadenantes y considere consultar a un profesional.\n\n");
                    break;
                case "Falta de concentración":
                    tips.append("Para la falta de concentración, establezca un horario y tome descansos regulares.\n\n");
                    break;
                case "Fatiga":
                    tips.append("Para la fatiga, asegúrese de dormir lo suficiente y mantener una dieta equilibrada.\n\n");
                    break;
                case "Horarios irregulares de sueño":
                    tips.append("Para los horarios irregulares de sueño, intente mantener una rutina de sueño constante.\n\n");
                    break;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Consejos")
                .setMessage(tips.toString())
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}