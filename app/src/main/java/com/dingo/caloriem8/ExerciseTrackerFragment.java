package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExerciseTrackerFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Context currContext;
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private User user;
    private Ejercicio e;
    private String key;

    private Calendar c;

    private Spinner sp_exercise;
    private TextView et_date;
    private Chronometer c_clock;
    private Button btn_start;
    private Button btn_end;

    ArrayAdapter<CharSequence> gAdapter;
    private Boolean run = false;
    private long stop;

    private int hour, min, restohora, bCalories;
    private int nHour, nMin;
    private float MET, calories=0, caloriesMin=0, weight;
    private String sDate, sExercise;
    private String sStartTime, sEndTime, amPm;

    public ExerciseTrackerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_tracker, container, false);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        e = new Ejercicio();
        Date date = new Date();

        et_date = view.findViewById(R.id.et_date);
        c_clock = view.findViewById(R.id.c_clock);
        btn_start = view.findViewById(R.id.btn_start);
        btn_end = view.findViewById(R.id.btn_end);

        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        sp_exercise = view.findViewById(R.id.sp_exercise);
        gAdapter = ArrayAdapter.createFromResource(currContext, R.array.exercise_catalog, android.R.layout.simple_spinner_item);
        gAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_exercise.setAdapter(gAdapter);
        sp_exercise.setOnItemSelectedListener(this);

        SimpleDateFormat df = new SimpleDateFormat("MM'/'dd'/'YYYY");
        sDate = df.format(date);
        System.out.println(sDate);
        et_date.setText(sDate);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!run){
                    c_clock.setBase(SystemClock.elapsedRealtime());
                    c_clock.start();
                    run = true;

                    setDate();
                    setHour();
                    sStartTime = String.format("%02d:%02d ", hour, min) + amPm;
                    System.out.println(sStartTime);
                }
            }
        });

        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(run){
                    c_clock.stop();
                    stop = SystemClock.elapsedRealtime() - c_clock.getBase();
                    run = false;
                    System.out.println(stop);

                    setNewHour();

                    hour = hour + nHour;
                    min = min + nMin;

                    setHour();
                    sEndTime = String.format("%02d:%02d ", hour, min) + amPm;
                    System.out.println("Tiempo = " + sEndTime);

                    if("Running".equals(sExercise))
                        MET = 8;
                    else if("Cycling".equals(sExercise))
                        MET = (float) 7.5;
                    else if("Walking".equals(sExercise))
                        MET = (float) 3.8;
                    else if("Yoga".equals(sExercise))
                        MET = (float) 2.5;
                    else if("Swimming".equals(sExercise))
                        MET = 7;
                    else
                        MET = 5;

                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user = dataSnapshot.child("Users").child(fAuth.getCurrentUser().getUid()).getValue(User.class);
                            weight = Float.parseFloat(user.getWeight());

                            caloriesMin = (MET * weight)/60;

                            if(hour != 0)
                                calories = ((MET * weight)*nHour);
                            for(int i=0; i<nMin; i++){
                                calories = (calories + caloriesMin);
                                //sCalories = Float.toString(calories);
                                //System.out.println("\nCalorias =" + sCalories+"\n");
                            }

                            bCalories = (int) calories;
                            final String sCalories = Integer.toString(bCalories);
                            System.out.println("Calorias srting = " + sCalories);
                            e.setBurned_Calories(sCalories);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    e.setExercise(sExercise);
                    e.setDate(sDate);
                    e.setStart_Time(sStartTime);
                    e.setEnd_Time(sEndTime);

                    dbRef.child("Exercise").child(fAuth.getCurrentUser().getUid().toString()).child("1").setValue(
                            new Ejercicio(e.getExercise(), e.getDate(), e.getStart_Time(), e.getEnd_Time(), e.getBurned_Calories())
                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(currContext, "Information Updated", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });



        return view;
    }

    public void setDate(){
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        System.out.println(hour + ":" + min);
    }

    public void setNewHour(){
        nHour = (int) stop/3600000;
        restohora = (int) stop%3600000;
        nMin = restohora/60000;

        System.out.println(nHour + ":" + nMin);
    }

    public void setHour(){
        if(min >= 60) {
            min = min % 60;
            hour++;
        }
        if (hour >= 12)
            amPm = "PM";
        else
            amPm = "AM";

        hour = hour % 12;
        if(hour == 0)
            hour = 12;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        currContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        currContext = null;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sExercise = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}