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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManDailyCaloriesFragment extends Fragment implements View.OnClickListener{
    public static final String EXTRA_INFO_ID = "infoId";
    private int infoId;
    private Context currContext;

    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    private Date dateToday;
    private String todayStdDateFormat;

    private DayInfo dayInfo;
    private String dayKey;

    private TextView date;
    private Button btnAddPlus;
    private Button btnAddMinus;
    private Button btnSubPlus;
    private Button btnSubMinus;
    private EditText etDataAdd;
    private EditText etDataSub;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {

        } else {
            infoId = savedInstanceState.getInt(EXTRA_INFO_ID);
        }

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        dateToday = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        todayStdDateFormat = df.format(dateToday);

        // REF -> https://stackoverflow.com/questions/41601147/get-last-node-in-firebase-database-android
        Query lastQuery = dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    dayInfo = ds.getValue(DayInfo.class);
                    dayKey = ds.getKey();
                }
                if(dayInfo.getDate().equals(todayStdDateFormat)) {
                    etDataAdd.setText(dayInfo.getCalsConsumed());
                    etDataSub.setText(dayInfo.getCalsBurned());
                } else {
                    dayKey = Integer.toString(Integer.parseInt(dayKey) + 1);
                    DayInfo todayValue = new DayInfo();
                    todayValue.setDate(todayStdDateFormat);
                    dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).child(dayKey).setValue(todayValue);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_man_daily_calories, container, false);

        date = view.findViewById(R.id.dayinfo_date);
        btnAddPlus = view.findViewById(R.id.dayinfo_add_plus);
        btnAddMinus = view.findViewById(R.id.dayinfo_add_minus);
        btnSubPlus = view.findViewById(R.id.dayinfo_sub_plus);
        btnSubMinus = view.findViewById(R.id.dayinfo_sub_minus);
        etDataAdd = view.findViewById(R.id.dayinfo_edit_add);
        etDataSub = view.findViewById(R.id.dayinfo_edit_sub);
        btnSubmit = view.findViewById(R.id.dayinfo_btnSubmit);

        SimpleDateFormat df = new SimpleDateFormat("E MMM dd yyyy");
        date.setText(df.format(dateToday));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String consumed = etDataAdd.getText().toString().trim();
                String burned = etDataSub.getText().toString().trim();

                if(TextUtils.isEmpty(consumed)) {
                    etDataAdd.setError("Invalid");
                    return;
                }
                try {
                    Integer.parseInt(consumed);
                }  catch (NumberFormatException e) {
                    etDataAdd.setError("Invalid");
                    return;
                }

                if(TextUtils.isEmpty(burned)) {
                    etDataSub.setError("Invalid");
                    return;
                }
                try {
                    Integer.parseInt(burned);
                }  catch (NumberFormatException e) {
                    etDataSub.setError("Invalid");
                    return;
                }

                DayInfo updatedInfo = dayInfo;
                updatedInfo.setDate(todayStdDateFormat);
                updatedInfo.setCalsConsumed(consumed);
                updatedInfo.setCalsBurned(burned);
                dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).child(dayKey).setValue(updatedInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(currContext, "Info Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnAddPlus.setOnClickListener(this);
        btnAddMinus.setOnClickListener(this);
        btnSubPlus.setOnClickListener(this);
        btnSubMinus.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int step = 1;
        int consumed;
        int burned;

        try {
            consumed = Integer.parseInt(etDataAdd.getText().toString().trim());
            burned = Integer.parseInt(etDataSub.getText().toString().trim());
        } catch(NumberFormatException ex) {
            ex.printStackTrace();
            return;
        }

        if(v == btnAddPlus) {
            etDataAdd.setText(Integer.toString(consumed + step));
        } else if(v == btnAddMinus) {
            if(consumed - step >= 0)
                etDataAdd.setText(Integer.toString(consumed - step));
        } else if(v == btnSubPlus) {
            etDataSub.setText(Integer.toString(burned + step));
        } else if(v == btnSubMinus) {
            if(burned - step >= 0)
                etDataSub.setText(Integer.toString(burned - step));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putInt(EXTRA_INFO_ID, infoId);
    }

    public void setInfoId(int id) {
        this.infoId = id;
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
