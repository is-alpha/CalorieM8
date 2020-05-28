package com.dingo.caloriem8;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */

public class ExerciseFragment extends Fragment {
    private Button btn_reg_excercise;
    private DatabaseReference dbRef;
    private FirebaseAuth firebaseAuth;

    private EditText et_date;
    private DatePickerDialog.OnDateSetListener dp_dateSetListener;
    private TextView et_start;
    private TextView et_end;
    private TimePickerDialog time;
    private Button btnSubmit;
    private Calendar c;
    private int hour;
    private int min;
    private String amPm;
    private String date;
    private String start;
    private String end;

    private Spinner spinner_excercise;
    private EditText et_calories;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);


        spinner_excercise = view.findViewById(R.id.spinner_excercise);
        et_calories = view.findViewById(R.id.et_calories);

        et_date = view.findViewById(R.id.et_date);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),
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

                time = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";

                        hourOfDay = hourOfDay % 12;
                        if(hourOfDay == 0)
                            hourOfDay = 12;
                        start = String.format("%02d:%02d ",hourOfDay,minutes)+amPm;
                        et_start.setText(start);
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

                time = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12)
                            amPm = "PM";
                        else
                            amPm = "AM";

                        hourOfDay = hourOfDay % 12;
                        if(hourOfDay == 0)
                            hourOfDay = 12;
                        end = String.format("%02d:%02d ",hourOfDay,minutes)+amPm;
                        et_end.setText(end);
                        //et_start.setText(hourOfDay+":"+minutes+amPm);
                    }
                }, hour, min, false);
                time.show();
            }
        });


        return view;
    }



}
