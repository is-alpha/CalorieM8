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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ManDailySleepFragment extends Fragment implements View.OnClickListener {
    private Context currContext;

    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    private Date dateToday;
    private String todayStdDateFormat;

    private DayInfo dayInfo;
    private String dayKey;

    private TextView date;
    private Button btnAvgPlus;
    private Button btnAvgMinus;
    private Button btnExtraPlus;
    private Button btnExtraMinus;
    private EditText etAvg;
    private EditText etExtra;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        dateToday = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        todayStdDateFormat = df.format(dateToday);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_man_daily_sleep, container, false);

        date = view.findViewById(R.id.dayinfo_date_sleep);
        btnAvgMinus = view.findViewById(R.id.dayinfo_add_minus_sleep);
        btnAvgPlus = view.findViewById(R.id.dayinfo_add_plus_sleep);
        btnExtraMinus = view.findViewById(R.id.dayinfo_sub_minus_sleep);
        btnExtraPlus = view.findViewById(R.id.dayinfo_sub_plus_sleep);
        etAvg = view.findViewById(R.id.dayinfo_edit_add_sleep);
        etExtra = view.findViewById(R.id.dayinfo_edit_sub_sleep);
        btnSubmit = view.findViewById(R.id.dayinfo_btnSubmit_sleep);

        SimpleDateFormat df = new SimpleDateFormat("E MMM dd yyyy");
        date.setText(df.format(dateToday));

        dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot prev, next;
                prev = next = null;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    prev = next;
                    next = ds;
                }

                dayInfo = next.getValue(DayInfo.class);
                dayKey = next.getKey();

                if(dayInfo.getDate().equals(todayStdDateFormat)) {
                    if(prev != null) {
                        DayInfo prvDay = new DayInfo(prev.getValue(DayInfo.class));
                        if(dayInfo.getAvgSleep().equals("null")) {
                            dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).child(dayKey).child("avgSleep").setValue(prvDay.getAvgSleep());
                        }

//                        if(dayInfo.getExtraSleep().equals("null")) {
//                            dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).child(dayKey).child("extraSleep").setValue(prvDay.getExtraSleep());
//                        }
                    }
                    etAvg.setText(dayInfo.getAvgSleep());
                    etExtra.setText(dayInfo.getExtraSleep());
                } else {
                    dayKey = Integer.toString(Integer.parseInt(dayKey) + 1);
                    DayInfo todayValue = new DayInfo();
                    todayValue.setDate(todayStdDateFormat);
                    if(prev != null){
                        DayInfo prevDayInfo = new DayInfo(prev.getValue(DayInfo.class));
                        todayValue.setAvgSleep(prevDayInfo.getAvgSleep());
                        todayValue.setExtraSleep(prevDayInfo.getExtraSleep());
                    }
                    dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).child(dayKey).setValue(todayValue);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String average = etAvg.getText().toString().trim();
                String extra = etExtra.getText().toString().trim();

                if(TextUtils.isEmpty(average)) {
                    etAvg.setError("Invalid");
                    return;
                }
                try {
                    Integer.parseInt(average);
                } catch (NumberFormatException e) {
                    etAvg.setError("Invalid");
                    return;
                }

                if(TextUtils.isEmpty(extra)) {
                    etExtra.setError("Invalid");
                    return;
                }
                try {
                    Integer.parseInt(extra);
                } catch (NumberFormatException e) {
                    etExtra.setError("Invalid");
                    return;
                }

                DayInfo updatedInfo = dayInfo;
                updatedInfo.setDate(todayStdDateFormat);
                updatedInfo.setAvgSleep(average);
                updatedInfo.setExtraSleep(extra);
                dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).child(dayKey).setValue(updatedInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(currContext, "Info Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnAvgPlus.setOnClickListener(this);
        btnAvgMinus.setOnClickListener(this);
        btnExtraPlus.setOnClickListener(this);
        btnExtraMinus.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int step = 1, average, extra;
        try {
            average = Integer.parseInt(etAvg.getText().toString().trim());
            extra = Integer.parseInt(etExtra.getText().toString().trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return;
        }

        if(v == btnAvgPlus) {
            etAvg.setText(Integer.toString(average + step));
        } else if(v == btnAvgMinus) {
            if(average - step > 0)
                etAvg.setText(Integer.toString(average - step));
        } else if(v == btnExtraPlus) {
            etExtra.setText(Integer.toString(extra + step));
        } else if(v == btnExtraMinus) {
            if(extra - step > 0)
                etExtra.setText(Integer.toString(extra - step));
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
}
