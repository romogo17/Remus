package com.una.optimizeprime.remus;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CreatorActivity extends AppCompatActivity implements View.OnClickListener {

    int step = 0;
    String exName = "";
    ArrayList<String> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator);

        findViewById(R.id.create_generalinfo).setVisibility(View.VISIBLE);
        findViewById(R.id.create_notes).setVisibility(View.GONE);

        findViewById(R.id.btn_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (step == 0) {
                    if (((TextView) findViewById(R.id.exercisename_fld)).getText().toString().isEmpty()) {
                        Snackbar.make(findViewById(R.id.main_creator), R.string.emptyexercisename_error, Snackbar.LENGTH_LONG)
                                .setAction(android.R.string.yes, null)
                                .show();
                        return;
                    }
                    findViewById(R.id.create_generalinfo).setVisibility(View.GONE);
                    findViewById(R.id.create_notes).setVisibility(View.VISIBLE);
                    ((Button) findViewById(R.id.btn_action)).setText(R.string.finish_label);

                    exName = ((EditText) findViewById(R.id.exercisename_fld)).getText().toString();
                    ((TextView) findViewById(R.id.heading_text)).setText(exName);
                    step++;

                } else if (step == 1) {
                    if (notes.size() < 5) {
                        Snackbar.make(findViewById(R.id.main_creator), R.string.empotynotes_error, Snackbar.LENGTH_LONG)
                                .setAction(android.R.string.yes, null)
                                .show();
                        return;
                    }
                    Exercise newExercise = new Exercise(getSelectedClef(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), getSelectedKey(), exName, notes, null);
                    Database.getInstance().writeExercise(newExercise);
                    step = 0;
                }
            }
        });
        findViewById(R.id.btn_c).setOnClickListener(this);
        findViewById(R.id.btn_d).setOnClickListener(this);
        findViewById(R.id.btn_e).setOnClickListener(this);
        findViewById(R.id.btn_f).setOnClickListener(this);
        findViewById(R.id.btn_g).setOnClickListener(this);
        findViewById(R.id.btn_a).setOnClickListener(this);
        findViewById(R.id.btn_b).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (notes.size() > 75) {
            Snackbar.make(findViewById(R.id.main_creator), R.string.toomanynotes_error, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.yes, null)
                    .show();
            return;
        }

        String newNote = "";
        switch (view.getId()) {
            case R.id.btn_c:
                newNote = "C";
                break;
            case R.id.btn_d:
                newNote = "D";
                break;
            case R.id.btn_e:
                newNote = "E";
                break;
            case R.id.btn_f:
                newNote = "F";
                break;
            case R.id.btn_g:
                newNote = "G";
                break;
            case R.id.btn_a:
                newNote = "A";
                break;
            case R.id.btn_b:
                newNote = "B";
                break;
            default:
                break;

        }
        if (!newNote.isEmpty()) {
            notes.add(newNote);
            ((TextView) findViewById(R.id.allnotes)).setText(getLocaleNoteArray());
        }
    }

    private String getSelectedClef() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.clef_group);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.rb_gclef:
                return "G";
            case R.id.rb_fclef:
                return "F";

        }
        return "G";
    }

    private String getSelectedKey() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.key_group);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        switch (checkedRadioButtonId) {
            case R.id.rb_cmajor:
                return "CM";
            case R.id.rb_dmajor:
                return "DM";
            case R.id.rb_emajor:
                return "EM";
            case R.id.rb_fmajor:
                return "FM";
            case R.id.rb_gmajor:
                return "GM";
            case R.id.rb_amajor:
                return "AM";
        }
        return "CM";
    }

    private int stringToResource(String s) {
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

    private String getLocaleNoteArray() {
        ArrayList<String> localeNotes = new ArrayList<>();

        for (String n : notes) {
            localeNotes.add(getText(stringToResource(n)).toString());
        }

        return localeNotes.toString();
    }
}
