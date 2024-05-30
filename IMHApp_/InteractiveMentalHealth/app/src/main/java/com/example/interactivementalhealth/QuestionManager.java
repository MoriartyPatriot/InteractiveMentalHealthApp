package com.example.interactivementalhealth;

import android.content.Context;
import android.content.SharedPreferences;

public class QuestionManager {

    private static final String PREF_NAME = "questions_pref";
    private static final String KEY_INDEX = "current_index";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String[] questions;

    public QuestionManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();

        // Definir las preguntas
        questions = new String[]{
                "Siempre me preocupa una cosa u otra.",
                "Hablo más y pienso más rápido que los demás.",
                "No siento que las cosas están bajo control a menos que yo esté a cargo.",
                "Soy rápido para actuar por capricho, pensando poco en las consecuencias negativas de mis acciones.",
                "A veces la gente me sonríe en la calle y no sé por qué.",
                "A veces pienso que tengo poderes que otras personas no podrían entender.",
                "Mis pensamientos saltan de un tema a otro con poca consistencia o control.",
                "Siempre me aseguro de que mi trabajo esté bien planeado y estructurado.",
                "Me siento mejor tomando grandes decisiones si consigo que otros sugieran un curso de acción y luego lo sigo.",
                "Mis pensamientos tienden a correr en ciertos bucles extraños y a pensar en temas raros, y desearía poder deshacerme de esto.",
                "La idea de no tener a nadie a mi lado que me cuide me da mucho miedo.",
                "Frecuentemente pierdo cosas, olvidando donde las pongo.",
                "Cuando empiezo a sentirme deprimido, es como si siguiera hundiéndome hasta tocar fondo.",
                "A menudo pienso que las interacciones con otras personas son más problemáticas de lo que merecen.",
                "Si algo bueno se cruza en mi camino, no me siento feliz por ello. No siento que merezca ser feliz."
        };
    }

    public int getCurrentIndex() {
        return preferences.getInt(KEY_INDEX, 0);
    }

    public String getCurrentQuestion() {
        return questions[getCurrentIndex()];
    }

    public void nextQuestion() {
        int currentIndex = getCurrentIndex();
        if (currentIndex < questions.length - 1) {
            editor.putInt(KEY_INDEX, currentIndex + 1);
            editor.apply();
        }
    }

    public void previousQuestion() {
        int currentIndex = getCurrentIndex();
        if (currentIndex > 0) {
            editor.putInt(KEY_INDEX, currentIndex - 1);
            editor.apply();
        }
    }

    public int getTotalQuestions() {
        return questions.length;
    }

    public void resetQuestions() {
        editor.putInt(KEY_INDEX, 0);
        editor.apply();
    }
}

