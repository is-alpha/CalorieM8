package com.dingo.caloriem8;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;



public class CrearMeta extends Fragment {

    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    private int d;
    private EditText start_date;
    private EditText end_date;
    private DatePickerDialog.OnDateSetListener dp_dateSetListener;
    private Context currContext;
    private String date;
    public int index=0;
    private Button btnSummit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_meta, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        btnSummit = view.findViewById(R.id.button_submit);
        start_date = view.findViewById(R.id.et_start_date);


        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = 1;
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

        end_date = view.findViewById(R.id.et_end_date);
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = 2;
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
                switch (d){
                    case 1: start_date.setText(date);break;
                    case 2: end_date.setText(date); break;

                }

            }
        };

        btnSummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new MetasFragment()).commit();
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


}
