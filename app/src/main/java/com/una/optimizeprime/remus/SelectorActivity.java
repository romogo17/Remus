package com.una.optimizeprime.remus;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SelectorActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        // Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

        // Set the toolbar
        setToolbar();

        // Set Activity drawer
        navigationView = setActivityDrawer();

        // Update the Navigation drawer according to the user
        updateNavigationDrawer();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // specify an adapter (see also next example)
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("G", "Roberto Mora", "CM", "Twinkle Twinkle Little Star", new ArrayList<String>(){{
                add("C");
                add("C");
                add("G");
                add("G");
                add("A");
                add("A");
                add("G");
            }}, 3));
        exercises.add(new Exercise("G", "Pedro Carazo", "CM", "C Major Scale", new ArrayList<String>(){{
                add("C");
                add("D");
                add("E");
                add("F");
                add("G");
                add("A");
                add("B");
            }}, 1));
        exercises.add(new Exercise("G", "Ketcha Hernandez", "CM", "Despacito", new ArrayList<String>(){{
                add("D");
                add("D");
                add("C");
                add("C");
            }}, 2));

        mAdapter = new RVAdapter(exercises, getResources(), getApplicationContext());
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }

    @NonNull
    private NavigationView setActivityDrawer() {
        // Set the activity drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        switch (menuItem.getItemId()){
                            case R.id.nav_sign_out:
                                mAuth.signOut();
                                // Starts a new activity of SignInActivity class
                                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.nav_sign_in:
                                // Starts a new activity of SignInActivity class
                                intent = new Intent(getApplicationContext(), SignInActivity.class);
                                startActivity(intent);
                                finish();
                                break;

                        }

                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {  // Respond when the drawer's position changes
                    }
                    @Override
                    public void onDrawerOpened(View drawerView) { // Respond when the drawer is opened
                    }
                    @Override
                    public void onDrawerClosed(View drawerView) { // Respond when the drawer is closed
                    }
                    @Override
                    public void onDrawerStateChanged(int newState) { // Respond when the drawer motion state changes
                    }
                }
        );
        return navigationView;
    }

    private void setToolbar() {
        // Set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }

    private void updateNavigationDrawer() {
        // Set the navbar options according to the logged user
        View header = navigationView.getHeaderView(0);
        TextView headerTitle = (TextView) header.findViewById(R.id.nav_header_title);

        if (mAuth.getCurrentUser() !=  null){
            // There is a user logged in
            navigationView.getMenu().findItem(R.id.nav_sign_in).setVisible(false);
            headerTitle.setText(mAuth.getCurrentUser().getDisplayName());

        } else {
            navigationView.getMenu().findItem(R.id.nav_sign_out).setVisible(false);
            headerTitle.setText(getString(R.string.guest_display_name));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
//            case R.id.goto_exercise:
//                //Starts a new activity of ExerciseActivity class
//                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
//                startActivity(intent);
//                break;
        }
    }
}
