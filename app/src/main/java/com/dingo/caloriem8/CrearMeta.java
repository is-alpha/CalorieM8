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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;



public class CrearMeta extends Fragment {

    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    private String date;
    private String cal;
    private String steps;

    private boolean nuevo;
    // elementos de fragment_crear_meta
    private static ArrayList<Meta> metas = new ArrayList<Meta>();
    private int ind;
    Meta meta = new Meta();
    private EditText et_date;
    private EditText et_calories;
    private EditText et_steps;
    private Button btnSummit;
    // ----------------------------------

    private Context currContext;

    public CrearMeta() {
        nuevo=true;
    }

    public CrearMeta(int ind){
        this.ind = ind;
        nuevo = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crear_meta, container, false);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        btnSummit = view.findViewById(R.id.button_submit);

        et_date =  view.findViewById(R.id.et_date);         // Asignando ID
        et_date.setKeyListener(null);

        et_calories = view.findViewById(R.id.et_calories);  // ASIGNANDO ID
        et_steps = view.findViewById(R.id.et_pasos);        // ASIGNANDO ID

        if(!metas.isEmpty() && nuevo==false){
            //et_calories.setText(metas.get(ind).getCalorias());
            et_steps.setText(metas.get(ind).getSteps());
        }


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

                cal= et_calories.getText().toString();
                steps = et_steps.getText().toString();
                /*
                metas.add(new Meta(date, cal, steps));
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new MetasFragment()).commit();
                */

                if(steps.isEmpty())
                    Toast.makeText(getContext(), "Ingresar meta de pasos", Toast.LENGTH_SHORT).show();
                else if(cal.isEmpty())
                    Toast.makeText(getContext(), "Ingresar meta de calorias", Toast.LENGTH_SHORT).show();
                else {
                    metas.add(new Meta(date, cal, steps));
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new MetasFragment()).commit();
                }


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
