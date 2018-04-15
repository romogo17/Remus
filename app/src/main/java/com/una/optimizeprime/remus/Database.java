package com.una.optimizeprime.remus;


import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.common.data.DataBufferObserver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Database extends Observable {
    private DatabaseReference mDatabase;
    private ArrayList<Exercise> exercises = null;

    public Database() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        exercises =  new ArrayList<>();
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public  void writeExercise(Exercise ex){
        mDatabase.child("exercises").child(String.valueOf(exercises.size())).setValue(ex);
    }

    public void writeExercises(List<Exercise> exs){
        if(exs.isEmpty()) return;
        mDatabase.child("exercises").setValue(exs);
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
                GenericTypeIndicator<ArrayList<Exercise>> t = new GenericTypeIndicator<ArrayList<Exercise>>() {};
                self.exercises = dataSnapshot.getValue(t);


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
