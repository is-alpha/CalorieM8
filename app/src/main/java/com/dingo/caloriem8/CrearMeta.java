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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;



public class CrearMeta extends Fragment {

    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    private int d;
    private EditText et_date;
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

        et_date =  view.findViewById(R.id.et_date);
        et_date.setKeyListener(null);
        final Calendar c = Calendar.getInstance();
        int anio = c.get(Calendar.YEAR); //obtenemos el año
        int mes = c.get(Calendar.MONTH); //obtenemos el mes

        mes = mes + 1;
        int dia = c.get(Calendar.DAY_OF_MONTH); // obtemos el día.
        date = dia + "/"+ mes + "/"+anio;
        et_date.setText(date);

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
