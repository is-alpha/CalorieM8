package com.dingo.caloriem8;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */

public class ExerciseFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Context currContext;
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private Ejercicio e;
    private String key;

    private EditText et_date;
    private TextView et_start;
    private TextView et_end;
    private Spinner sp_exercise;
    private EditText et_calories;
    private Button btnSubmit;
    private DatePickerDialog.OnDateSetListener dp_dateSetListener;
    private TimePickerDialog time;
    private Calendar c;

    private int hour;
    private int min;

    ArrayAdapter<CharSequence> gAdapter;
    private String amPm;
    private String exercise;
    private String date;
    private String start_time;
    private String end_time;
    private int change = 0;

    public ExerciseFragment() {
        // Required empty public constructor
        change = 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        // REF -> https://stackoverflow.com/questions/41601147/get-last-node-in-firebase-database-android
        Query lastQuery = dbRef.child("Exercise").child(fAuth.getCurrentUser().getUid());
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    e = ds.getValue(Ejercicio.class);
                    key = ds.getKey();

                    System.out.println("key: " + key);
                    System.out.println("Exercise :" + e.getExercise());
                    System.out.println("start time: " + e.getStart_Time());
                    System.out.println("end time: " + e.getEnd_Time());
                    System.out.println(" ");


                    if (key.equals("3")) {
                        key = "1";
                    } else {
                         String exercise = e.getExercise();
                         String date = e.getDate();
                         String start_Time =  e.getStart_Time();
                         String end_Time = e.getEnd_Time();
                         String burned_Calories = e.getBurned_Calories();

                        key = Integer.toString(Integer.parseInt(key) + 1);
                        dbRef.child("Exercise").child(fAuth.getCurrentUser().getUid()).child(key).setValue(new Ejercicio(exercise,
                                date,start_Time,end_Time,burned_Calories));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        e = new Ejercicio();

        et_calories = view.findViewById(R.id.et_calories);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        sp_exercise = view.findViewById(R.id.spinner_excercise);
        gAdapter = ArrayAdapter.createFromResource(currContext, R.array.exercise_catalog, android.R.layout.simple_spinner_item);
        gAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_exercise.setAdapter(gAdapter);
        sp_exercise.setOnItemSelectedListener(this);

        et_date = view.findViewById(R.id.et_date);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(currContext,
                        R.style.Theme_AppCompat_Light_Dialog,
                        dp_dateSetListener,
                        year, month, day);
                dialog.getWindow();
                dialog.show();
            }
        });

        dp_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = month + "/" + dayOfMonth + "/" + year;
                et_date.setText(date);
            }
        };


        et_start = view.findViewById(R.id.et_start);
        et_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                min = c.get(Calendar.MINUTE);

                time = new TimePickerDialog(currContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";

                        hourOfDay = hourOfDay % 12;
                        if(hourOfDay == 0)
                            hourOfDay = 12;
                        start_time = String.format("%02d:%02d ", hourOfDay, minutes) + amPm;
                        et_start.setText(start_time);
                        //et_start.setText(hourOfDay+":"+minutes+amPm);
                    }
                }, hour, min, false);
                time.show();
            }
        });

        et_end = view.findViewById(R.id.et_end);
        et_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                min = c.get(Calendar.MINUTE);

                time = new TimePickerDialog(currContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";

                        hourOfDay = hourOfDay % 12;
                        if(hourOfDay == 0)
                            hourOfDay = 12;
                        end_time = String.format("%02d:%02d ", hourOfDay, minutes) + amPm;
                        et_end.setText(end_time);
                        //et_start.setText(hourOfDay+":"+minutes+amPm);
                    }
                }, hour, min, false);
                time.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String calories = et_calories.getText().toString().trim();

                if(exercise == null) {
                    return;
                }

                if(TextUtils.isEmpty(date)) {
                    et_date.setError("Invalid date");
                    return;
                }

                if(TextUtils.isEmpty(start_time)) {
                    et_start.setError("Invalid start time");
                    return;
                }

                if(TextUtils.isEmpty(end_time)) {
                    et_end.setError("Invalid end time");
                    return;
                }

                if(TextUtils.isEmpty(calories)) {
                    et_calories.setError("Invalid end time");
                    return;
                }
                try {
                    Float.parseFloat(calories);
                }catch (NumberFormatException e) {
                    et_calories.setError("Invalid calories");
                    return;
                }

                e.setExercise(exercise);
                e.setDate(date);
                e.setStart_Time(start_time);
                e.setEnd_Time(end_time);
                e.setBurned_Calories(calories);

                dbRef.child("Exercise").child(fAuth.getCurrentUser().getUid().toString()).child("1").setValue(
                        new Ejercicio(e.getExercise(), e.getDate(), e.getStart_Time(), e.getEnd_Time(), e.getBurned_Calories())
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(currContext, "Information Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
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
        exercise = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


