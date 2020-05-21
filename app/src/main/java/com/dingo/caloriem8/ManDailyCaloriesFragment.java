package com.dingo.caloriem8;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ManDailyCaloriesFragment extends Fragment {
    public static final String EXTRA_INFO_ID = "infoId";
    private int infoId;

    private DayInfo dayInfo;

    private TextView date;
    private TextView tvDataAdd;
    private TextView tvDataSub;
    private Button btnAddPlus;
    private Button btnAddMinus;
    private Button btnSubPlus;
    private Button btnSubMinus;
    private EditText etDataAdd;
    private EditText etDataSub;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {

        } else {
            infoId = savedInstanceState.getInt(EXTRA_INFO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_man_daily_calories, container, false);

        date = view.findViewById(R.id.dayinfo_date);
        tvDataAdd = view.findViewById(R.id.dayinfo_data_add);
        tvDataSub = view.findViewById(R.id.dayinfo_data_sub);
        btnAddPlus = view.findViewById(R.id.dayinfo_add_plus);
        btnAddMinus = view.findViewById(R.id.dayinfo_add_minus);
        btnSubPlus = view.findViewById(R.id.dayinfo_sub_plus);
        btnSubMinus = view.findViewById(R.id.dayinfo_sub_minus);
        etDataAdd = view.findViewById(R.id.dayinfo_edit_add);
        etDataSub = view.findViewById(R.id.dayinfo_edit_sub);

        SimpleDateFormat df = new SimpleDateFormat("E MMM dd yyyy");
        date.setText(df.format(Calendar.getInstance().getTime()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if(view != null) {

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt(EXTRA_INFO_ID, infoId);
    }

    public void setInfoId(int id) {
        this.infoId = id;
    }
}
