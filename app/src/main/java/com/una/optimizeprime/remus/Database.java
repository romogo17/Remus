package com.una.optimizeprime.remus;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Database extends Observable {

    private static Database instance = null;

    private DatabaseReference mDatabase;
    private ArrayList<Exercise> exercises = null;

    private Database() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        exercises =  new ArrayList<>();
    }

    public static Database getInstance(){
        if(instance == null){
            instance = new Database();
        }
        return instance;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public  void writeExercise(Exercise ex){
        mDatabase.child("exercises").push().setValue(ex);
    }

    public void writeExercises(List<Exercise> exs){
        if(exs.isEmpty()) return;
        for(Exercise ex : exs)
            writeExercise(ex);
    }

    public  void updateExercise(Exercise ex){
        mDatabase.child("exercises").child(ex.getId()).setValue(ex);
    }

    public void subscribeToExercises(SelectorActivity selectorActivity) {
        addObserver(selectorActivity);
        final Database self = this;
        // Read from the database
        mDatabase.child("exercises").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                /**
                 * Use this to read the data as an array
                 */
                //GenericTypeIndicator<ArrayList<Exercise>> t = new GenericTypeIndicator<ArrayList<Exercise>>() {};
                //self.exercises = dataSnapshot.getValue(t);

                /**
                 * Read the data as key pair values
                 */
                Exercise readExercise;
                self.exercises.clear();
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    readExercise = snap.getValue(Exercise.class);
                    readExercise.setExerciseId(snap.getKey());
                    self.exercises.add(readExercise);
                }

                self.setChanged();
                self.notifyObservers(self);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Exercise", "Failed to read value.", error.toException());
            }
        });
    }
}
