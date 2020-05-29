package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MetasFragment extends Fragment {

    private Meta m;
    private Context currContext;
    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;
    private EditText et_steps;
    private EditText et_consumedCalories;
    private EditText et_burnedCalories;
    private Button btn_submit;
    private int on = 0;

    public MetasFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_metas, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        m = new Meta();

        et_steps = view.findViewById(R.id.et_steps);
        et_consumedCalories = view.findViewById(R.id.et_consumedCalories);
        et_burnedCalories = view.findViewById(R.id.et_burnedCalories);
        btn_submit = view.findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String steps = et_steps.getText().toString().trim();
                final String consumedCalories = et_consumedCalories.getText().toString().trim();
                final String burnedCalories = et_burnedCalories.getText().toString().trim();

                if(TextUtils.isEmpty(steps)) {
                    et_steps.setError("Invalid steps");
                    return;
                }
                try {
                    Float.parseFloat(steps);
                }catch (NumberFormatException e) {
                    et_steps.setError("Invalid steps");
                    return;
                }

                if(TextUtils.isEmpty(consumedCalories)) {
                    et_consumedCalories.setError("Invalid consumed calories");
                    return;
                }
                try {
                    Float.parseFloat(consumedCalories);
                }catch (NumberFormatException e) {
                    et_consumedCalories.setError("Invalid consumed Calories");
                    return;
                }

                if(TextUtils.isEmpty(burnedCalories)) {
                    et_burnedCalories.setError("Invalid burned calories");
                    return;
                }
                try {
                    Float.parseFloat(burnedCalories);
                }catch (NumberFormatException e) {
                    et_burnedCalories.setError("Invalid burned calories");
                    return;
                }

                m.setSteps(steps);
                m.setConsumedCalories(consumedCalories);
                m.setBurnedCalories(burnedCalories);

                dbRef.child("Metas").child(fAuth.getCurrentUser().getUid().toString()).setValue(
                        new Meta(m.getSteps(), m.getConsumedCalories(), m.getBurnedCalories()
                        )).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(currContext, "Information Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        currContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        currContext = null;
    }

}
