package com.una.optimizeprime.remus;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    Exercise exercise;
    ArrayList<String> options = new ArrayList<String>(){{
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
        aux = count+"/"+total;
        num_answers.setText(aux);

        OnclickDelButton(R.id.option_1);
        OnclickDelButton(R.id.option_2);
        OnclickDelButton(R.id.option_3);
        OnclickDelButton(R.id.option_4);

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

        play(index);
    }

    public void Mensaje(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void OnclickDelButton(int ref) {
        View view = findViewById(ref);
        Button miButton = (Button) view;
        miButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String opc;
                switch (v.getId()) {
                    case R.id.option_1:
                        //Mensaje("Implementar Button1");
                        opc = option_1.getTag().toString();
                        if(opc.equals(note)) {
                            score++;
                            points.setText(String.valueOf(score));
                        }
                        break;
                    case R.id.option_2:
                        //Mensaje("Implementar Button2");
                        opc = option_2.getTag().toString();
                        if(opc.equals(note)) {
                            score++;
                            points.setText(String.valueOf(score));
                        }
                        break;
                    case R.id.option_3:
                        //Mensaje("Implementar Button3");
                        opc = option_3.getTag().toString();
                        if(opc.equals(note)) {
                            score++;
                            points.setText(String.valueOf(score));
                        }
                        break;
                    case R.id.option_4:
                        //Mensaje("Implementar Button4");
                        opc = option_4.getTag().toString();
                        if(opc.equals(note)) {
                            score++;
                            points.setText(String.valueOf(score));
                        }
                        break;
                    default: break;
                }
                count++;
                String aux;
                aux = count+"/"+total;
                num_answers.setText(aux);
                index++;
                if(index < total)
                    play(index);
                //else
                    //end game
            }
        });
    }

    private void play(int i){
        note = notes.get(i);
        note_image.setImageResource(stringToDrawable(note));
        createOptions();
    }

    //Crea las opciones
    private void createOptions(){
        int num_option;   //Numero de opcion del array de opciones
        int pos_option = (int) (Math.random() * 4) + 1;  //Posicion en la que se va a colocar la opcion correcta en la vista
        ArrayList<String> aux = (ArrayList<String>) options.clone();
        aux.remove(note);
        putButtonText(pos_option,note);
        int pos = 1;
        for(int j=0; j<4; j++){
            num_option = (int) (Math.random() * aux.size());
            if(pos == pos_option)
                pos++;
            putButtonText(pos,aux.get(num_option));
            aux.remove(num_option);
            pos++;
        }
    }

    //Coloca la opcion en el boton
    private void putButtonText(int pos, String text){
        switch (pos){
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


    public int stringToDrawable(String s){
        switch (s){
            case "C": return R.drawable.note_gclef_c;
            case "D": return R.drawable.note_gclef_d;
            case "E": return R.drawable.note_gclef_e;
            case "F": return R.drawable.note_gclef_f;
            case "G": return R.drawable.note_gclef_g;
            case "A": return R.drawable.note_gclef_a;
            case "B": return R.drawable.note_gclef_b;
        }
        return R.drawable.note_gclef_c;
    }

    public int stringToResource(String s){
        switch (s){
            case "C": return R.string.C;
            case "D": return R.string.D;
            case "E": return R.string.E;
            case "F": return R.string.F;
            case "G": return R.string.G;
            case "A": return R.string.A;
            case "B": return R.string.B;
        }
        return R.string.C;
    }
}
