package com.una.optimizeprime.remus;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ExerciseActivity extends AppCompatActivity {

    Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);


        Intent intent = getIntent();
        exercise = (Exercise) intent.getExtras().getParcelable("Exercise");

        /**
         * Esto es solo de prueba para verificar que se esta pasando el objeto correcto
         *
         *
         */
        Snackbar.make(findViewById(R.id.exercise_layout), "Exercise is: " + exercise.getName(), Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
        /**
         *
         * FIN DE PRUEBA
         *
         */
    }
}
