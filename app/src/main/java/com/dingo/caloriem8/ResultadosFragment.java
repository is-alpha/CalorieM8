package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    private View resultadosfragContainer;

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

        resultadosfragContainer = view.findViewById(R.id.main_fragment_container);

        card_IMC.setOnClickListener(this);
        card_Weight.setOnClickListener(this);
        card_burnedCal.setOnClickListener(this);
        card_consumedCal.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if(v == card_IMC)
                ft.replace(R.id.main_fragment_container, new ImcFragment());
            else if(v == card_Weight)
                ft.replace(R.id.main_fragment_container, new WeightFragment());
            else if(v == card_burnedCal)
                ft.replace(R.id.main_fragment_container, new BurnedCaloriesFragment());
            else if( v == card_consumedCal)
                ft.replace(R.id.main_fragment_container, new ConsumedCaloriesFragment());


            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
