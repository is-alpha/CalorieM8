package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExerciseTrackerFragment extends Fragment {
    private Context currContext;
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;

    private Calendar c;

    private TextView et_date;
    private Chronometer c_clock;
    private Button btn_start;
    private Button btn_end;

    private Boolean run = false;
    private long stop;

    private int hour, min, restohora;
    private int nHour, nMin;
    private String sDate;
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

        Date date = new Date();

        et_date = view.findViewById(R.id.et_date);
        c_clock = view.findViewById(R.id.c_clock);
        btn_start = view.findViewById(R.id.btn_start);
        btn_end = view.findViewById(R.id.btn_end);

        c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

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

                    c = Calendar.getInstance();
                    hour = c.get(Calendar.HOUR_OF_DAY);
                    min = c.get(Calendar.MINUTE);
                    System.out.println(hour + ":" + min);

                    if (hour >= 12)
                        amPm = "PM";
                    else
                        amPm = "AM";

                    hour = hour % 12;
                    if(hour == 0)
                        hour = 12;
                    System.out.println(sStartTime = String.format("%02d:%02d ", hour, min) + amPm);
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

                    nHour = (int) stop/3600000;
                    restohora = (int) stop%3600000;
                    nMin = restohora/60000;


                    System.out.println(nHour + ":" + nMin);

                    hour = hour + nHour;
                    min = min + nMin;

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
                    System.out.println(sEndTime = String.format("%02d:%02d ", hour, min) + amPm);
                }
            }
        });



        return view;
    }
}