package com.una.optimizeprime.remus;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ExerciseViewHolder> {

    List<Exercise> exercises;
    public Resources resources;
    public Context context;

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView exName;
        TextView exClef;
        TextView exKey;
        TextView exCreatedBy;
        RatingBar exStars;

        public ExerciseViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.card_view);
            exName = (TextView) itemView.findViewById(R.id.exercise_name);
            exClef = (TextView) itemView.findViewById(R.id.exercise_clef);
            exKey = (TextView) itemView.findViewById(R.id.exercise_key);
            exCreatedBy = (TextView) itemView.findViewById(R.id.exercise_created_by);
            exStars = (RatingBar) itemView.findViewById(R.id.exercise_stars);
        }
    }


    RVAdapter(List<Exercise> exercises, Resources resources, Context context){
        this.exercises = exercises;
        this.resources = resources;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent,
                                                 final int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ExerciseViewHolder(itemView);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, final int i) {
        Exercise exercise = exercises.get(i);
        holder.exName.setText(exercise.getName());
        holder.exClef.setText(stringToResource(exercise.getClef()));
        holder.exKey.setText(stringToResource(exercise.getKey()));
        holder.exCreatedBy.setText(resources.getString(R.string.added_by, exercise.getCreated_by()));
        holder.exStars.setRating(exercise.getStars());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starts a new activity of ExerciseActivity class
                Intent intent = new Intent(context, ExerciseActivity.class);
                intent.putExtra("Exercise", exercises.get(i));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("DEBUG!", "the count is " + exercises.size());
        return exercises.size();

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
            case "CM": return R.string.CM;
        }
        return R.string.C;
    }

}
