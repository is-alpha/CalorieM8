package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultadosFragment extends Fragment implements View.OnClickListener{

    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private Button btnIMC;
    private Button btn_consumedCal;
    private Button btn_burnedCal;
    private Button btnWeight;

    public ResultadosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resultados_submenu, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        btnIMC = view.findViewById(R.id.frp_btnIMC);
        btn_burnedCal = view.findViewById(R.id.frp_btn_burnedCalories);
        btn_consumedCal = view.findViewById(R.id.frp_btn_consumedCalories);

        btnIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ImcFragment()).commit();
            }
        });

        btn_consumedCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new GoalsFragment()).commit();
            }
        });

        btn_burnedCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("HEEEEEEEEEEEEEEEEEELLLLLLLLLLLLLOOOOOOOOOOOOOOOOOOOOO");
            }
        });

        btnWeight = view.findViewById(R.id.frp_btnPeso);

        btnIMC.setOnClickListener(this);
        btnWeight.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        if(v == btnIMC)
            getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ImcFragment()).commit();
        else if(v == btnWeight)
            getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new WeightFragment()).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
