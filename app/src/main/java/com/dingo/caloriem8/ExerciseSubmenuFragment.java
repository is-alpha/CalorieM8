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

public class ExerciseSubmenuFragment extends Fragment implements View.OnClickListener{
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private CardView card_AddActivity;
    private CardView card_ExerciseTracker;
    private View exercisefragContainer;

    public ExerciseSubmenuFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_submenu, container, false);

        //base de datos
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        card_AddActivity = (CardView) view.findViewById(R.id.card_AddActivity);
        card_ExerciseTracker = (CardView) view.findViewById(R.id.card_ExerciseTracker);

        exercisefragContainer = view.findViewById(R.id.main_fragment_container);

        card_AddActivity.setOnClickListener(this);
        card_ExerciseTracker.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(v == card_AddActivity)
            ft.replace(R.id.main_fragment_container, new ExerciseFragment());
        else if(v == card_ExerciseTracker)
            ft.replace(R.id.main_fragment_container, new ExerciseTrackerFragment());

        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}