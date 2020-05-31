package com.dingo.caloriem8;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*This class customizes the view of the RecyclerView class on the MenuListFragment*/

public class CustomFoodAdapter extends ArrayAdapter<Food> {

    public CustomFoodAdapter(Context context, ArrayList<Food> foods){
        super(context, 0, foods);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_food, parent, false);
        }

        //get data item from this position
        Food food = getItem(position);

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_menu_name);
        TextView tvServings = (TextView) convertView.findViewById(R.id.tv_menu_servings);
        TextView tvCalories = (TextView) convertView.findViewById(R.id.tv_menu_calories);
        TextView tvFat = (TextView) convertView.findViewById(R.id.tv_menu_fat);
        TextView tvCarbs = (TextView) convertView.findViewById(R.id.tv_menu_carbs);
        TextView tvFiber = (TextView) convertView.findViewById(R.id.tv_menu_fiber);
        TextView tvProtein = (TextView) convertView.findViewById(R.id.tv_menu_protein);

        // Populate the data into the template view using the data object
        tvName.setText(food.getName());
        tvServings.setText(String.valueOf(food.getServing()));
        tvCalories.setText(String.valueOf(food.getCalories()));
        tvFat.setText(String.valueOf(food.getFat()));
        tvCarbs.setText(String.valueOf(food.getCarbs()));
        tvFiber.setText(String.valueOf(food.getFiber()));
        tvProtein.setText(String.valueOf(food.getProtein()));


        // Return the completed view to render on screen
        return convertView;
    }
}
