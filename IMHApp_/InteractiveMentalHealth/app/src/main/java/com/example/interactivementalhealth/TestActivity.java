package com.example.interactivementalhealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {

    private QuestionManager questionManager;
    private TextView textViewQuestionNumber;
    private TextView textViewQuestion;
    private int yesCount = 0;
    private int noCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        questionManager = new QuestionManager(this);
        questionManager.resetQuestions();  // Reiniciar el índice de la pregunta

        yesCount = 0;
        noCount = 0;

        ImageView buttonBack = findViewById(R.id.backButton);
        Button buttonYes = findViewById(R.id.buttonYes);
        Button buttonNo = findViewById(R.id.buttonNo);
        Button buttonPrevious = findViewById(R.id.buttonPrevious);
        Button buttonNext = findViewById(R.id.buttonNext);
        textViewQuestionNumber = findViewById(R.id.textViewQuestion);
        textViewQuestion = findViewById(R.id.textViewQuestion2);

        updateQuestion();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesCount++;
                nextQuestion();
            }
        });

        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noCount++;
                nextQuestion();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionManager.previousQuestion();
                updateQuestion();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
    }

    private void nextQuestion() {
        if (questionManager.getCurrentIndex() < questionManager.getTotalQuestions() - 1) {
            questionManager.nextQuestion();
            updateQuestion();
        } else {
            String diagnosis = getDiagnosis();
            Intent intent = new Intent(TestActivity.this, DiagnosisActivity.class);
            intent.putExtra("DIAGNOSIS", diagnosis);
            startActivity(intent);
            finish();
        }
    }

    private void updateQuestion() {
        int currentIndex = questionManager.getCurrentIndex();
        textViewQuestionNumber.setText("Pregunta " + (currentIndex + 1) + " de " + questionManager.getTotalQuestions() + ":");
        textViewQuestion.setText(questionManager.getCurrentQuestion());
    }

    private String getDiagnosis() {
        // Lógica básica para el diagnóstico preliminar
        if (yesCount >= 10) {
            return "Esquizofrenia";
        } else if (yesCount >= 8) {
            return "TDAH";
        } else if (yesCount >= 6) {
            return "TDA";
        } else if (yesCount >= 4) {
            return "ANSIEDAD";
        } else if (yesCount >= 2) {
            return "TOC";
        } else if (yesCount == 1) {
            return "AUTISMO";
        } else {
            return "DEPRESION";
        }
    }
}
