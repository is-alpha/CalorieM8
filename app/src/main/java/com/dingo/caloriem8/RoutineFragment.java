package com.dingo.caloriem8;

import android.graphics.Color;
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
    private Meta m;

    private Spinner sp_exercises;
    private TextView rtv_date;
    private TextView rtv_startTime;
    private TextView rtv_endTime;
    private TextView rtv_burnedCalories;
    private TextView rtv_totalCalories;

    private int c1, c2, c3, total, meta;

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
        rtv_totalCalories = view.findViewById(R.id.rtv_totalCalories);

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

                                    e = dataSnapshot.child("Exercise").child(fAuth.getCurrentUser().getUid()).child("1").getValue(Ejercicio.class);
                                    c1 = Integer.parseInt(e.getBurned_Calories());
                                    e = dataSnapshot.child("Exercise").child(fAuth.getCurrentUser().getUid()).child("2").getValue(Ejercicio.class);
                                    c2 = Integer.parseInt(e.getBurned_Calories());
                                    e = dataSnapshot.child("Exercise").child(fAuth.getCurrentUser().getUid()).child("3").getValue(Ejercicio.class);
                                    c3 = Integer.parseInt(e.getBurned_Calories());

                                    total = c1 + c2 + c3;

                                    m = dataSnapshot.child("Metas").child(fAuth.getCurrentUser().getUid()).getValue(Meta.class);

                                    meta = Integer.parseInt(m.getBurnedCalories());

                                    rtv_totalCalories.setText(Integer.toString(total));

                                    if(total >= meta)
                                        rtv_totalCalories.setTextColor(Color.parseColor("#5FF750"));
                                    else
                                        rtv_totalCalories.setTextColor(Color.parseColor("#FF5050"));

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