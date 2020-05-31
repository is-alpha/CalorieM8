package com.dingo.caloriem8;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImcFragment extends Fragment {

    private EditText mi_IMC;
    private User user;
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private int weight;
    private float height,imc, diferencia, pesoMin, pesoMax;
    private TextView tv_imc, tv_peso, tv_pesoIdeal;
    private ImageView iv_goBack;
    private TextView tv_weight;

    public ImcFragment() {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_imc, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        iv_goBack = view.findViewById(R.id.iv_goBack);
        iv_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ResultadosFragment()).commit();
            }
        });

        mi_IMC = view.findViewById(R.id.frp_IMC);
        mi_IMC.setKeyListener(null);
        tv_imc = view.findViewById(R.id.tv_imc);
        tv_peso = view.findViewById(R.id.tv_peso);
        tv_pesoIdeal = view.findViewById(R.id.tv_pesoIdeal);
        tv_weight = view.findViewById(R.id.tv_weight);

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
                tv_weight.setText("Your weight: " + user.getWeight().toString() + " kg");

                height = (Float.parseFloat(user.getHeight()))/100; // porque debe estar en mts.
                weight = Integer.parseInt(user.getWeight());
                imc = weight/(height*height);

                mi_IMC.setText(Float.toString(imc));

                pesoMin = (float) (22.1 * (height*height));
                pesoMax = (float) (24.9 * (height*height));

                if(weight < pesoMin){
                    diferencia = (float) (pesoMin - weight);
                    tv_peso.setText("-"+String.format("%.2f", diferencia)+"kg");
                    tv_peso.setTextColor(Color.parseColor("#FF5050"));
                }
                else if(weight > pesoMax){
                    diferencia = (float) (weight - pesoMax);
                    tv_peso.setText("+"+String.format("%.2f", diferencia)+"kg");
                    tv_peso.setTextColor(Color.parseColor("#FF5050"));
                }
                else{
                    tv_peso.setText("✔");
                    tv_peso.setTextColor(Color.parseColor("#5FF750"));
                }

                tv_pesoIdeal.setText("Ideal normal weight:    "+String.format("%.2f", pesoMin)+" - "+String.format("%.2f", pesoMax)+" kg");

                if(imc <= 18.4){
                    tv_imc.setText("Malnutrition");
                    tv_imc.setTextColor(Color.parseColor("#FF5050"));
                }
                else if(imc <= 22) {
                    tv_imc.setText("Underweight");
                    tv_imc.setTextColor(Color.parseColor("#FF5050"));
                }
                else if(imc < 25) {
                    tv_imc.setText("Normal weight");
                    tv_imc.setTextColor(Color.parseColor("#5FF750"));
                }
                else if(imc < 30) {
                    tv_imc.setText("Overweight");
                    tv_imc.setTextColor(Color.parseColor("#FF5050"));
                }
                else {
                    tv_imc.setText("Obese");
                    tv_imc.setTextColor(Color.parseColor("#FF5050"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
