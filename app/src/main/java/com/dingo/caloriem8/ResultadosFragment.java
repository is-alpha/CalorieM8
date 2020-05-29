package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultadosFragment extends Fragment implements View.OnClickListener{

    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private CardView card_IMC;
    private CardView card_consumedCal;
    private CardView card_burnedCal;
    private CardView card_Weight;

    public ResultadosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resultados_submenu, container, false);
        //base de datos
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        //botones
        card_IMC = (CardView) view.findViewById(R.id.card_IMC);
        card_burnedCal = (CardView) view.findViewById(R.id.card_burnedCalories);
        card_consumedCal = (CardView) view.findViewById(R.id.card_consumedCalories);
        card_Weight = (CardView) view.findViewById(R.id.card_weight);

        card_IMC.setOnClickListener(this);
        card_Weight.setOnClickListener(this);
        card_burnedCal.setOnClickListener(this);
        card_consumedCal.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        if(v == card_IMC)
            getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ImcFragment()).commit();
        else if(v == card_Weight)
            getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new WeightFragment()).commit();
        else if(v == card_burnedCal)
            getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new BurnedCaloriesFragment()).commit();
        else if( v == card_consumedCal)
            getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ConsumedCaloriesFragment()).commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
