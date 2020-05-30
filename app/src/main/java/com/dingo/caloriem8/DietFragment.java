package com.dingo.caloriem8;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DietFragment extends Fragment implements View.OnClickListener{
    private Context dietContext;
    private Button btnDietMenu;
    private Button btnNewFood;
    private View dietFragContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        btnDietMenu = view.findViewById(R.id.fd_food_menu);
        btnNewFood = view.findViewById(R.id.fd_food_db);
        dietFragContainer = view.findViewById(R.id.fd_fragment_container);

        btnDietMenu.setOnClickListener(this);
        btnNewFood.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(dietFragContainer != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if(v == btnDietMenu) {
                btnNewFood.setTextColor(Color.WHITE);
                btnDietMenu.setTextColor(ContextCompat.getColor(dietContext, R.color.colorAccent));
                ft.replace(R.id.fd_fragment_container, new MenuListFragment());
            } else if(v == btnNewFood) {
                btnDietMenu.setTextColor(Color.WHITE);
                btnNewFood.setTextColor(ContextCompat.getColor(dietContext, R.color.colorAccent));
                ft.replace(R.id.fd_fragment_container, new AddNewFoodFragment());
            }

            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dietContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dietContext = null;
    }
}
