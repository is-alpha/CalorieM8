package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultadosFragment extends Fragment {

    private EditText mi_IMC;
    private User user;
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private int weight;
    private float height,imc;

    public ResultadosFragment() {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resultados, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        mi_IMC = view.findViewById(R.id.frp_IMC);
        mi_IMC.setKeyListener(null);


        return  view;

    }


    @Override
    public void onStart() {
        super.onStart();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child("Users").child(fAuth.getCurrentUser().getUid()).getValue(User.class);
                /* Formula para sacar IMC
                    peso/altura^2
                    peso en KG
                    altura en Mts

                 */
                height = (Float.parseFloat(user.getHeight()))/100; // porque debe estar en mts.
                weight = Integer.parseInt(user.getWeight());
                imc = weight/(height*height);
                mi_IMC.setText(Float.toString(imc));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
