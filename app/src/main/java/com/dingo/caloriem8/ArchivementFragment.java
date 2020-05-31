package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ArchivementFragment extends Fragment {

    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;

    private Ejercicio e;
    private Meta m;

    private String key;
    private String c;
    private String cals_burned;
    private int calories = 0;

    private EditText et_burned_calories;

    public ArchivementFragment() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_archivement, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        et_burned_calories = view.findViewById(R.id.et_burnedCaloriesExercise);
        System.out.println("Total Calories2 " + calories);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();


        et_burned_calories.setText(Integer.toString(calories));
        Query lastQuery = dbRef.child("Exercise").child(fAuth.getCurrentUser().getUid());
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    e = ds.getValue(Ejercicio.class);
                    key = ds.getKey();

                    System.out.println("key: " + key);
                    System.out.println("Exercise :" + e.getExercise() + " Calories: " + e.getBurned_Calories());

                    c = e.getBurned_Calories();
                    calories += Integer.parseInt(c);
                    System.out.println("Total Calories " + calories);

                }

                et_burned_calories.setText(Integer.toString(calories));
                System.out.println("quemada: " + cals_burned);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}