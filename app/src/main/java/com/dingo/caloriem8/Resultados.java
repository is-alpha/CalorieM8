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
public class Resultados extends Fragment {

    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private Button btnIMC;

    public Resultados() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resultados_submenu, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        btnIMC = view.findViewById(R.id.frp_btnIMC);

        btnIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ImcFragment()).commit();
            }
        });

        return  view;

    }

    @Override
    public void onStart() {
        super.onStart();


    }

}
