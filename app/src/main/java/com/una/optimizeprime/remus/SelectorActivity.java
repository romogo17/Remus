package com.una.optimizeprime.remus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<Exercise> originalExercises = new ArrayList<>();
    ArrayList<Exercise> exercises = new ArrayList<>();
    ArrayList<Exercise> newList = new ArrayList<>();

    Database db;

    RecyclerView.LayoutManager layoutManager;

    private String m_Text;

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
            case R.id.action_search:
                openSearch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    filter(m_Text = input.getText().toString());

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void Mensaje(String msg){
        View v1 = getWindow().getDecorView().getRootView();
        AlertDialog.Builder builder1 = new AlertDialog.Builder( v1.getContext());
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {} });
        AlertDialog alert11 = builder1.create();
        alert11.show();
        ;};

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
        //OriginalDates
        //this.originalExercises.addAll(db.getExercises());
        // Add all the new ones
        this.exercises.addAll(db.getExercises());
        // Notify the change to the adapter
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        return true;

    }

    private void filter(String text) {

        if(text.length() == 0){
            this.originalExercises.addAll(db.getExercises());
        }else{
            final  String name = text.toString().toLowerCase().trim();
            for (final Exercise exercise : exercises) {
                //if the existing elements contains the search input
                if(exercise.getName().toLowerCase().contains(name)) {
                    //adding the element to filtered list
                    newList.add(exercise);
                }
            }
        }
        exercises.clear();
        exercises.addAll(newList);
        mAdapter.notifyDataSetChanged();
    }

}