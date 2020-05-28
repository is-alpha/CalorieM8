package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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
        btnWeight = view.findViewById(R.id.frp_btnPeso);

        /*AGREGA LOS LISTENER ASI!!!! PARA CAMBIO DE FRAGMENT!!!! ESTA SUPER MIERDA HACER LO DIRECTO!!!!*/
        /*ES VALIDO PONERLE UN ONCLICKLISTENER DIRECTO PERO LO ESTAN HACIENDO SIN LOS PASOS CORRECTOS*/
        /*NOMAS SIGANLO HACIENDO COMO LO HAGO ABAJO !!!*/
        /*DE ESTA FORMA EL BOTON DE BACK TE REGRESA AL MENU DE RESULTFRAGMENTS Y NO TE SACA DE LA APP SUPER MIERDA*/
        btnIMC.setOnClickListener(this);
        btnWeight.setOnClickListener(this);
        btn_consumedCal.setOnClickListener(this);
        btn_burnedCal.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(v == btnIMC) {
            ft.replace(R.id.main_fragment_container, new ImcFragment());
        } else if(v == btnWeight) {
            ft.replace(R.id.main_fragment_container, new WeightFragment());
        } else if(v == btn_consumedCal) {
            ft.replace(R.id.main_fragment_container, new GoalsFragment());
        } else if(v == btn_burnedCal) {

        }
        
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
