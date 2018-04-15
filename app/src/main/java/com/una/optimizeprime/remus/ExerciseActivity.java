package com.una.optimizeprime.remus;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    Exercise exercise;
    ArrayList<String> options = new ArrayList<String>() {{
        add("A");
        add("B");
        add("C");
        add("D");
        add("E");
        add("F");
        add("G");
    }};
    String note;
    ArrayList<String> notes;
    Button option_1;
    Button option_2;
    Button option_3;
    Button option_4;
    TextView points;
    TextView num_answers;
    ImageView note_image;
    int index = 0;
    int score = 0;
    int count = 0;
    int total = 0;
    long start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent intent = getIntent();
        exercise = (Exercise) intent.getExtras().getParcelable("Exercise");

        option_1 = findViewById(R.id.option_1);
        option_2 = findViewById(R.id.option_2);
        option_3 = findViewById(R.id.option_3);
        option_4 = findViewById(R.id.option_4);
        points = findViewById(R.id.points);
        num_answers = findViewById(R.id.num_answer);
        note_image = findViewById(R.id.note_image);

        notes = exercise.getNotes();
        total = notes.size();

        points.setText(String.valueOf(score));
        String aux;
        aux = count + "/" + total;
        num_answers.setText(aux);

        findViewById(R.id.option_1).setOnClickListener(this);
        findViewById(R.id.option_2).setOnClickListener(this);
        findViewById(R.id.option_3).setOnClickListener(this);
        findViewById(R.id.option_4).setOnClickListener(this);

        snackbarMessage(getString(R.string.exercise_is, exercise.getName()));

        play(index);
    }

    public void snackbarMessage(String msg) {
        Snackbar.make(findViewById(R.id.exercise_layout), msg, Snackbar.LENGTH_LONG)
                .setAction(android.R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
    }


    @Override
    public void onClick(View view) {
        String opc;
        switch (view.getId()) {
            case R.id.option_1:
                //Mensaje("Implementar Button1");
                opc = option_1.getTag().toString();
                if (opc.equals(note)) {
                    score++;
                    points.setText(String.valueOf(score));
                }
                break;
            case R.id.option_2:
                //Mensaje("Implementar Button2");
                opc = option_2.getTag().toString();
                if (opc.equals(note)) {
                    score++;
                    points.setText(String.valueOf(score));
                }
                break;
            case R.id.option_3:
                //Mensaje("Implementar Button3");
                opc = option_3.getTag().toString();
                if (opc.equals(note)) {
                    score++;
                    points.setText(String.valueOf(score));
                }
                break;
            case R.id.option_4:
                //Mensaje("Implementar Button4");
                opc = option_4.getTag().toString();
                if (opc.equals(note)) {
                    score++;
                    points.setText(String.valueOf(score));
                }
                break;
            default:
                break;
        }
        count++;
        String aux = count + "/" + total;
        num_answers.setText(aux);
        if(index == 0){
            start = System.nanoTime();
        }
        index++;
        if (index < total)
            play(index);
        else{
            end = System.nanoTime();
            rateExercise();
        }

    }

    private void play(int i) {
        note = notes.get(i);
        note_image.setImageResource(stringToDrawable(note));
        createOptions();
    }

    //Crea las opciones
    private void createOptions() {
        int num_option;   //Numero de opcion del array de opciones
        int pos_option = (int) (Math.random() * 4) + 1;  //Posicion en la que se va a colocar la opcion correcta en la vista
        ArrayList<String> aux = (ArrayList<String>) options.clone();
        aux.remove(note);
        putButtonText(pos_option, note);
        int pos = 1;
        for (int j = 0; j < 4; j++) {
            num_option = (int) (Math.random() * aux.size());
            if (pos == pos_option)
                pos++;
            putButtonText(pos, aux.get(num_option));
            aux.remove(num_option);
            pos++;
        }
    }

    //Coloca la opcion en el boton
    private void putButtonText(int pos, String text) {
        switch (pos) {
            case 1:
                option_1.setText(stringToResource(text));
                option_1.setTag(text);
                break;
            case 2:
                option_2.setText(stringToResource(text));
                option_2.setTag(text);
                break;
            case 3:
                option_3.setText(stringToResource(text));
                option_3.setTag(text);
                break;
            case 4:
                option_4.setText(stringToResource(text));
                option_4.setTag(text);
                break;
        }
    }


    public int stringToDrawable(String s) {
        switch (s) {
            case "C":
                return R.drawable.note_gclef_c;
            case "D":
                return R.drawable.note_gclef_d;
            case "E":
                return R.drawable.note_gclef_e;
            case "F":
                return R.drawable.note_gclef_f;
            case "G":
                return R.drawable.note_gclef_g;
            case "A":
                return R.drawable.note_gclef_a;
            case "B":
                return R.drawable.note_gclef_b;
        }
        return R.drawable.note_gclef_c;
    }

    public int stringToResource(String s) {
        switch (s) {
            case "C":
                return R.string.C;
            case "D":
                return R.string.D;
            case "E":
                return R.string.E;
            case "F":
                return R.string.F;
            case "G":
                return R.string.G;
            case "A":
                return R.string.A;
            case "B":
                return R.string.B;
        }
        return R.string.C;
    }

    public void rateExercise() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rating, null);
        final float sc = (float)score/(float)total * 100f;
        final long milliseconds = (end - start) / 1000000;

        TextView scoreView = (TextView) view.findViewById(R.id.your_score), timeView = (TextView) view.findViewById(R.id.your_time);
        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.your_rating);
        scoreView.setText(String.valueOf(sc));
        timeView.setText(millisecondsFormated(milliseconds));

        builder.setTitle(R.string.rate_alert_title)
                //.setMessage(R.string.without_acc_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // save the rating to the database
                        if (FirebaseAuth.getInstance().getCurrentUser() != null){
                            exercise.addScore(new Score(milliseconds, sc, ratingBar.getRating(), FirebaseAuth.getInstance().getCurrentUser().getUid()));
                            Database.getInstance().updateExercise(exercise);
                        }

                        // Return to the selector class
                        Intent intent = new Intent(getApplicationContext(), SelectorActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Return to the selector class
                        Intent intent = new Intent(getApplicationContext(), SelectorActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .setView(view);

        builder.show();
    }

    @SuppressLint("DefaultLocale")
    private String millisecondsFormated(long milliseconds){
        return String.format("%dm, %ds",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds))
        );

    }

}
