package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DayInfoFragment extends Fragment {
    public static final String EXTRA_INFOID = "infoId";
    private int infoId;

    public static final String[] optionTitles = {"Calories", "Sleep"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {

        } else {
            infoId = savedInstanceState.getInt(EXTRA_INFOID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view != null) {
            TextView title = (TextView)view.findViewById(R.id.dayinfo_textTitle);
            title.setText(optionTitles[infoId]);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt(EXTRA_INFOID, infoId);
    }

    public void setInfoId(int id) {
        this.infoId = id;
    }
}
