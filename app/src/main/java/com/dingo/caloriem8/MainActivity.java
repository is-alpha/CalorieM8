package com.dingo.caloriem8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    public FirebaseAuth fAuth;
    public DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // navHeader = nv.inflateHeaderView(R.layout.nav_header); Este No!!
        View navHeader = nv.getHeaderView(0);
        TextView tvEmail = navHeader.findViewById(R.id.navh_email);
        tvEmail.setText(fAuth.getCurrentUser().getEmail());

        final TextView tvUsername = navHeader.findViewById(R.id.navh_username);
        dbRef.child("Users").child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User usr = dataSnapshot.getValue(User.class);
                tvUsername.setText(usr.getDisplayName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new HomeFragment()).commit();
            nv.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new HomeFragment()).commit();
                break;

            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.nav_exercise:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ExerciseFragment()).commit();
                break;

            case R.id.nav_results:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ResultadosFragment()).commit();

                break;

            case R.id.nav_achievements:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ArchivementFragment()).commit();
                break;

            case R.id.nav_routine:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new RoutineFragment()).commit();
                break;

            case R.id.nav_goals:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new MetasFragment()).commit();
                break;

            case R.id.nav_diet:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new DietFragment()).commit();
                break;

            case R.id.nav_scanner:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ScannerFragment()).commit();
                break;

            case R.id.nav_version:
                Toast.makeText(this, "1.0", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                logout();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fAuth.signOut();
        finish();
    }

    // public void logout(View view)
    public void logout() {
        fAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}
