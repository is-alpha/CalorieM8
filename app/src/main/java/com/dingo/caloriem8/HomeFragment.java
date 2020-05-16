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

public class HomeFragment extends Fragment {
    /*** HACER QUE SIEMPRE ESTE PUESTO UNA DE LAS OPCIOENS DE LOS CARD VIEWS EN EL HOME FRAGMENT CONTAINER PARA QUE NO SE APACHURREN LOS BOTONES ***/
    private Context homeContext;
    private CardView crdCalories;
    private View fragContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        crdCalories = (CardView)view.findViewById(R.id.home_crd_calories);
        fragContainer = view.findViewById(R.id.home_fragment_container);

        crdCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragContainer != null) {
                    DayInfoFragment dayInfo = new DayInfoFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dayInfo.setInfoId(0);
                    ft.replace(R.id.home_fragment_container, dayInfo);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
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
}
