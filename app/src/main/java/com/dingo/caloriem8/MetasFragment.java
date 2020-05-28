package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MetasFragment extends Fragment {

    private User user;
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private EditText et_steps;
    private EditText et_consumedCalories;
    private EditText et_burnedCalories;
    private Button btn_submit;

    public MetasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_metas, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        et_steps = view.findViewById(R.id.et_steps);
        et_consumedCalories = view.findViewById(R.id.et_consumedCalories);
        et_burnedCalories = view.findViewById(R.id.et_burnedCalories);
        btn_submit = view.findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Information Updated", Toast.LENGTH_SHORT).show();
            }
        });

        return  view;

    }

    @Override
    public void onStart() {

        super.onStart();


    }

}
