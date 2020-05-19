package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment implements View.OnClickListener{
    /*** HACER QUE SIEMPRE ESTE PUESTO UNA DE LAS OPCIOENS DE LOS CARD VIEWS EN EL HOME FRAGMENT CONTAINER PARA QUE NO SE APACHURREN LOS BOTONES ***/
    private Context homeContext;
    private CardView crdCalories;
    private CardView crdSleep;
    private CardView crdWeight;
    private CardView crdExercise;
    private View fragContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        crdCalories = (CardView)view.findViewById(R.id.home_crd_calories);
        crdSleep = (CardView)view.findViewById(R.id.home_crd_sleep);
        crdWeight = (CardView)view.findViewById(R.id.home_crd_weight);
        crdExercise = (CardView)view.findViewById(R.id.home_crd_exercise);
        fragContainer = view.findViewById(R.id.home_fragment_container);

        crdCalories.setOnClickListener(this);
        crdSleep.setOnClickListener(this);
        crdWeight.setOnClickListener(this);
        crdExercise.setOnClickListener(this);

        selectOptionFragContainer(0);


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        homeContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeContext = null;
    }

    @Override
    public void onClick(View v) {
        int option = 0;
        if(v == crdCalories) {
            option = 0;
        } else if(v == crdSleep) {
            option = 1;
        } else if(v == crdWeight) {
            option = 2;
        } else if(v == crdExercise) {
            option = 3;
        }
        selectOptionFragContainer(option);
    }

    private void selectOptionFragContainer(int id) {
        if(fragContainer != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            switch (id) {
                case 0:
                case 1:
                    DayInfoFragment dayInfo = new DayInfoFragment();
                    dayInfo.setInfoId(id);
                    ft.replace(R.id.home_fragment_container, dayInfo);
                    break;

                case 2:
                    break;
                case 3:
                    break;

                default:
            }
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
    }
}
