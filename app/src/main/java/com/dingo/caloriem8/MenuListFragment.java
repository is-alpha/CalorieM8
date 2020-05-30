package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/*https://github.com/codepath/android-custom-array-adapter-demo/tree/master/app/src/main/java/com/codepath/example/customadapterdemo*/

public class MenuListFragment extends Fragment {
    private CalM8SQLiteHelper calM8SQLiteHelper;
    private Context currContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);
        calM8SQLiteHelper = new CalM8SQLiteHelper(currContext);

        ListView lvMenuList;

        ArrayList<String> arrMenu = new ArrayList<>();

        /*Sacar de la BD*/
        arrMenu.add("Str1");
        arrMenu.add("caca2");
        arrMenu.add("dasdasd3");

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(currContext, android.R.layout.simple_list_item_1, arrMenu);
        lvMenuList = (ListView) view.findViewById(R.id.fml_menulist);
        lvMenuList.setAdapter(itemsAdapter);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        calM8SQLiteHelper.close();
    }
}