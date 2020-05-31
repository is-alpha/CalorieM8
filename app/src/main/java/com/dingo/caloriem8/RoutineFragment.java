package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoutineFragment extends Fragment {
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private Ejercicio e;

    private Spinner sp_exercises;
    private TextView rtv_date;
    private TextView rtv_startTime;
    private TextView rtv_endTime;
    private TextView rtv_burnedCalories;

    public RoutineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine, container, false);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        e = new Ejercicio();
        sp_exercises = (Spinner) view.findViewById(R.id.sp_exercises);
        rtv_date = view.findViewById(R.id.rtv_date);
        rtv_startTime = view.findViewById(R.id.rtv_startTime);
        rtv_endTime = view.findViewById(R.id.rtv_endTime);
        rtv_burnedCalories = view.findViewById(R.id.rtv_burnedCalories);

        final List<Ejercicio> exercises = new ArrayList<>();

        dbRef.child("Exercise").child(fAuth.getCurrentUser().getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String id = ds.getKey();
                        String nombre = ds.child("exercise").getValue().toString();

                        exercises.add(new Ejercicio(id, nombre));
                    }
                    final ArrayAdapter<Ejercicio> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, exercises);
                    sp_exercises.setAdapter(arrayAdapter);
                    sp_exercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                            final String i = exercises.get(position).getId();

                            dbRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    e = dataSnapshot.child("Exercise").child(fAuth.getCurrentUser().getUid()).child(i).getValue(Ejercicio.class);
                                    rtv_date.setText(e.getDate());
                                    rtv_startTime.setText(e.getStart_Time());
                                    rtv_endTime.setText(e.getEnd_Time());
                                    rtv_burnedCalories.setText(e.getBurned_Calories());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}