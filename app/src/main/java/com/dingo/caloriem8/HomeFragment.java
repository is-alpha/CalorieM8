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

        // sin backstack(null) para desactivar back antes de este punto
        // Sin esto al presionar back se ven solo los cardViews
        if(fragContainer != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ManDailyCaloriesFragment caloriesFrag = new ManDailyCaloriesFragment();
            caloriesFrag.setInfoId(0);
            ft.replace(R.id.home_fragment_container, caloriesFrag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

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
                    ManDailyCaloriesFragment caloriesFrag = new ManDailyCaloriesFragment();
                    caloriesFrag.setInfoId(id);
                    ft.replace(R.id.home_fragment_container, caloriesFrag);
                    break;
                case 1:
                    ManDailySleepFragment manDailySleepFrag = new ManDailySleepFragment();
                    ft.replace(R.id.home_fragment_container, manDailySleepFrag);
                    break;

                case 2:
                    ManDailyWeightFragment manDailyWeightFrag= new ManDailyWeightFragment();
                    ft.replace(R.id.home_fragment_container, manDailyWeightFrag);
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
