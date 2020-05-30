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


public class ManDailyWeightFragment extends Fragment implements View.OnClickListener {
    private Context currContext;

    private DatabaseReference dbRef;
    private FirebaseAuth fAuth;

    private DayInfo dayInfo;
    private String dayKey;
    private Date dateToday;
    private String todayStdDateFormat;

    private TextView tvDate;
    private EditText etWeight;
    private Button btnDec;
    private Button btnInc;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        dateToday = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        todayStdDateFormat = df.format(dateToday);

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
                    etWeight.setText(dayInfo.getWeight());
                } else {
                    dayKey = Integer.toString(Integer.parseInt(dayKey) + 1);
                    DayInfo todayValue = new DayInfo();
                    todayValue.setDate(todayStdDateFormat);
                    if(prev != null) {
                        DayInfo prevDayInfo = prev.getValue(DayInfo.class);
                        todayValue.setWeight(prevDayInfo.getWeight());
                    }
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
        View view = inflater.inflate(R.layout.fragment_man_daily_weight, container, false);

        tvDate = view.findViewById(R.id.dayinfo_date_weight);
        etWeight = view.findViewById(R.id.dayinfo_edit_weight);
        btnDec = view.findViewById(R.id.dayinfo_dec_weight);
        btnInc = view.findViewById(R.id.dayinfo_inc_weight);
        btnSubmit = view.findViewById(R.id.dayinfo_btnSubmit_weight);

        SimpleDateFormat df = new SimpleDateFormat("E MMM dd yyyy");
        tvDate.setText(df.format(dateToday));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = etWeight.getText().toString().trim();

                if(TextUtils.isEmpty(weight)) {
                    etWeight.setError("Invalid");
                    return;
                }
                try {
                    Integer.parseInt(weight);
                } catch (NumberFormatException e) {
                    etWeight.setError("Invalid");
                    return;
                }

                final DayInfo updatedInfo = dayInfo;
                updatedInfo.setDate(todayStdDateFormat);
                updatedInfo.setWeight(weight);
                dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).child(dayKey).setValue(updatedInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Por alguna razon no lo actualiza
                        dbRef.child("Users").child(fAuth.getCurrentUser().getUid()).child("weight").setValue(updatedInfo.getWeight());
                        Toast.makeText(currContext, "Info Updated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnInc.setOnClickListener(this);
        btnDec.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int step = 1, weight;
        try {
            weight = Integer.parseInt(etWeight.getText().toString().trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return;
        }

        if(v == btnInc) {
            etWeight.setText(Integer.toString(weight + step));
        } else if(v == btnDec) {
            if(weight - step > 0)
                etWeight.setText(Integer.toString(weight - step));
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
