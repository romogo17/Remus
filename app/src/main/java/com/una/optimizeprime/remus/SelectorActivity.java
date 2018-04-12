package com.una.optimizeprime.remus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectorActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        findViewById(R.id.goto_exercise).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.goto_exercise:
                // Starts a new activity of ExerciseActivity class
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
