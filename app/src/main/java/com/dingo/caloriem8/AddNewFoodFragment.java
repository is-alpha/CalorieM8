package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewFoodFragment extends Fragment {
    private CalM8SQLiteHelper calM8SQLiteHelper;
    Context currContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_food, container, false);

        calM8SQLiteHelper = new CalM8SQLiteHelper(currContext);

        final EditText etName = view.findViewById(R.id.fanf_name);
        final EditText etServing = view.findViewById(R.id.fanf_serving);
        final EditText etCalories = view.findViewById(R.id.fanf_calories);
        final EditText etFat = view.findViewById(R.id.fanf_fat);
        final EditText etCarbs = view.findViewById(R.id.fanf_carbs);
        final EditText etFiber = view.findViewById(R.id.fanf_fiber);
        final EditText etProtein = view.findViewById(R.id.fanf_protein);
        Button btnSubmit = view.findViewById(R.id.fanf_btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String serving = etServing.getText().toString().trim();
                String calories = etCalories.getText().toString().trim();
                String fat = etFat.getText().toString().trim();
                String carbs = etCarbs.getText().toString().trim();
                String fiber = etFiber.getText().toString().trim();
                String protein = etProtein.getText().toString().trim();

//                Toast.makeText(currContext, Integer.toString(calM8SQLiteHelper.numRows()), Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(name)) {
                    etName.setError("Invalid");
                    return;
                }

                if(TextUtils.isEmpty(serving)) {
                    etServing.setError("Invalid");
                    return;
                }
                try {
                    Integer.parseInt(serving);
                } catch (NumberFormatException e) {
                    etServing.setError("Invalid");
                    return;
                }

                if(TextUtils.isEmpty(calories)) {
                    etCalories.setError("Invalid");
                    return;
                }
                try {
                    Integer.parseInt(calories);
                } catch (NumberFormatException e) {
                    etCalories.setError("Invalid");
                    return;
                }

                if(TextUtils.isEmpty(fat)) {
                    etFat.setError("Invalid");
                    return;
                }
                try {
                    Float.parseFloat(fat);
                } catch (NumberFormatException e) {
                    etFat.setError("Invalid");
                    return;
                }

                if(TextUtils.isEmpty(carbs)) {
                    etCarbs.setError("Invalid");
                    return;
                }
                try {
                    Float.parseFloat(carbs);
                } catch (NumberFormatException e) {
                    etCarbs.setError("Invalid");
                    return;
                }

                if(TextUtils.isEmpty(fiber)) {
                    etFiber.setError("Invalid");
                    return;
                }
                try {
                    Float.parseFloat(fiber);
                } catch (NumberFormatException e) {
                    etFiber.setError("Invalid");
                    return;
                }

                if(TextUtils.isEmpty(protein)) {
                    etProtein.setError("Invalid");
                    return;
                }
                try {
                    Float.parseFloat(protein);
                } catch (NumberFormatException e) {
                    etProtein.setError("Invalid");
                    return;
                }

                if(calM8SQLiteHelper.insertFood(name, Integer.parseInt(serving), Integer.parseInt(calories), Float.parseFloat(fat), Float.parseFloat(carbs), Float.parseFloat(fiber), Float.parseFloat(protein))) {
                    Toast.makeText(currContext, "Item Added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
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
