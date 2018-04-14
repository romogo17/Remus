package com.una.optimizeprime.remus;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ExerciseViewHolder> {

    List<Exercise> exercises;

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView exName;
        TextView exClef;
        TextView exKey;
        TextView exCreatedBy;
        ImageView exStars;

        public ExerciseViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.card_view);
            exName = (TextView) itemView.findViewById(R.id.exercise_name);
            exClef = (TextView) itemView.findViewById(R.id.exercise_clef);
            exKey = (TextView) itemView.findViewById(R.id.exercise_key);
            exCreatedBy = (TextView) itemView.findViewById(R.id.exercise_created_by);
            exStars = (ImageView) itemView.findViewById(R.id.exercise_stars);
        }
    }


    RVAdapter(List<Exercise> exercises){
        this.exercises = exercises;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ExerciseViewHolder(itemView);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int i) {
        Exercise exercise = exercises.get(i);
        holder.exName.setText(exercise.getName());
        holder.exClef.setText(exercise.getClef());
        holder.exKey.setText(exercise.getKey());
        holder.exCreatedBy.setText(exercise.getCreated_by());

        switch (Math.round(exercise.getStars())){
            case 1: holder.exStars.setImageResource(R.drawable.ic_star_border_black_24dp); break;
            case 2: holder.exStars.setImageResource(R.drawable.ic_star_half_black_24dp); break;
            case 3: holder.exStars.setImageResource(R.drawable.ic_star_black_24dp); break;
            default: holder.exStars.setImageResource(R.drawable.ic_star_border_black_24dp); break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.d("DEBUG!", "the count is " + exercises.size());
        return exercises.size();

    }

}
