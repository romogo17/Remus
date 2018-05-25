package com.una.optimizeprime.remus;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.ContactsContract;
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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SelectorActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    private DrawerLayout mDrawerLayout;
    private FirebaseAuth mAuth;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NavigationView navigationView;
    ArrayList<Exercise> exercises = new ArrayList<>();
    Database db;


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

        // Configure the recycler view
        configureRecyclerView();

        db = Database.getInstance();
        db.subscribeToExercises(this);
    }

    private void configureRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // specify an adapter (see also next example)
        exercises = new ArrayList<>();

        mAdapter = new RVAdapter(exercises, getResources(), getApplicationContext(), this);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
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
                        switch (menuItem.getItemId()) {
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
                            case R.id.nav_create:
                                // Starts a new activity of SignInActivity class
                                intent = new Intent(getApplicationContext(), CreatorActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_about:
                                // Starts a new activity of SignInActivity class
                                intent = new Intent(getApplicationContext(), AboutActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_video:
                                watchYoutubeVideo(getApplicationContext(), "stxmJ8qeCuM");
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

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
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

        if (mAuth.getCurrentUser() != null) {
            // There is a user logged in
            navigationView.getMenu().findItem(R.id.nav_sign_in).setVisible(false);
            headerTitle.setText(mAuth.getCurrentUser().getDisplayName());
            navigationView.getMenu().findItem(R.id.nav_create).setVisible(true);


        } else {
            navigationView.getMenu().findItem(R.id.nav_sign_out).setVisible(false);
            headerTitle.setText(getString(R.string.guest_display_name));
            navigationView.getMenu().findItem(R.id.nav_create).setVisible(false);
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
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mDrawerLayout.isDrawerOpen(navigationView)) {
                mDrawerLayout.closeDrawers();
            } else {
                finish();
                //mDrawerLayout.openDrawer(Gravity.START);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.goto_exercise:
//                //Starts a new activity of ExerciseActivity class
//                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        // Clear the old data set
        this.exercises.clear();
        // Add all the new ones
        this.exercises.addAll(db.getExercises());
        // Notify the change to the adapter
        mAdapter.notifyDataSetChanged();
    }

}
