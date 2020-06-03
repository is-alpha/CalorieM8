package com.dingo.caloriem8;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/*https://github.com/codepath/android-custom-array-adapter-demo/tree/master/app/src/main/java/com/codepath/example/customadapterdemo*/

public class MenuListFragment extends Fragment {
    private FirebaseAuth fAuth;
    private CalM8SQLiteHelper calM8SQLiteHelper;
    private Context currContext;
    private ListView listView;
    private TextView tvCalCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         fAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        tvCalCount = view.findViewById(R.id.fml_calorie_count);
        calM8SQLiteHelper = new CalM8SQLiteHelper(currContext);

        listView = (ListView)view.findViewById(R.id.fml_menulist);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Food foodToDelete = (Food)adapterView.getItemAtPosition(position);
                if(calM8SQLiteHelper.deleteFood(foodToDelete.getId())) {
                    listView.setAdapter(getUpdatedAdapter(view));
                    updateCalorieCount(view);
                }
            }
        });
        listView.setAdapter(getUpdatedAdapter(view));

        updateCalorieCount(view);

        Button btnSendEmail = view.findViewById(R.id.fml_send_email);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        //ListView lvMenuList;
        //RecyclerView rvMenuList;

        //ArrayList<String> menuList = new ArrayList();

        /*Saca de la BD*/
        /*menuList.add("Food1");
        menuList.add("Food2");
        menuList.add("Food3");*/

        /*ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(currContext, android.R.layout.simple_list_item_1, menuList);
        lvMenuList = (ListView) view.findViewById(R.id.fml_menulist);
        lvMenuList.setAdapter(itemsAdapter);*/
        return view;
    }

    // Hacer mil queries muchas veces va ser lento y no se debe hacer
    private CustomFoodAdapter getUpdatedAdapter(View view) {
        return new CustomFoodAdapter(currContext, calM8SQLiteHelper.getAllFoods());
    }

    private void updateCalorieCount(View view) {
        int calCount = 0;
        for(Food food : calM8SQLiteHelper.getAllFoods()) {
            calCount += food.getCalories();
        }
        tvCalCount.setText("Calorie Count: " + Integer.toString(calCount));
    }

    private void sendEmail() {
        String recipient = fAuth.getCurrentUser().getEmail();
        String subject = "CalorieM8 - Today's Menu List";
        StringBuilder msgBuilder = new StringBuilder();

        for(Food food : calM8SQLiteHelper.getAllFoods()) {
            msgBuilder.append(food.getName() + "\r\n");
            msgBuilder.append("\tServing: "  + food.getServing() + "g\r\n");
            msgBuilder.append("\tCalories: " + food.getCalories() + "\r\n");
            msgBuilder.append("\tFat: " + food.getFat() + "g\r\n");
            msgBuilder.append("\tCarbohydrates: " + food.getCarbs() + "g\r\n");
            msgBuilder.append("\tFiber: " + food.getFiber() + "g\r\n");
            msgBuilder.append("\tProtein: " + food.getProtein() + "g\r\n");
            msgBuilder.append("\r\n");
        }

        String message = msgBuilder.toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, recipient);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        emailIntent.setType("message/rfc822");
        try {
            startActivity(Intent.createChooser(emailIntent, "Choose an Email client"));
        }catch(android.content.ActivityNotFoundException ex) {
            Toast.makeText(currContext, "No Email clients installed", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        calM8SQLiteHelper.close();
    }
}