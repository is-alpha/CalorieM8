package com.dingo.caloriem8;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.dingo.caloriem8.ManDailyCaloriesFragment.EXTRA_INFO_ID;



public class ConsumedCaloriesFragment extends Fragment {

    private FirebaseAuth fAuth;
    private String todayStdDateFormat;
    private String cals_consumed;
    private DatabaseReference dbRef;
    private TextView tv_consumed_calories;
    private ProgressBar pb_burned_calories;
    private int pStatus;
    private int calories=0;
    private ImageView iv_goBack;
    private DayInfo dayInfo;
    private Date dateToday;
    private Meta metas;

    public ConsumedCaloriesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_consumed_calories, container,false);

        pb_burned_calories = (ProgressBar) view.findViewById(R.id.progressBar_goals_consumed_calories);
        tv_consumed_calories = (TextView) view.findViewById(R.id.tv_goals_consumed_calories);
        iv_goBack = view.findViewById(R.id.iv_goBack);

        iv_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ResultadosFragment()).commit();
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();
        dateToday = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        todayStdDateFormat = df.format(dateToday);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                metas = dataSnapshot.child("Metas").child(fAuth.getCurrentUser().getUid()).getValue(Meta.class);
                cals_consumed = metas.getConsumedCalories();
                System.out.println("caloria consumida :"+ cals_consumed);

                // final Query lastQuery = dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).orderByKey().limitToLast(1);
                Query query = dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            dayInfo = ds.getValue(DayInfo.class);
                            //calories += Integer.parseInt(dayInfo.getCalsBurned());
                            System.out.println("Date: " + dayInfo.getDate() + " Cals consumed:"+ dayInfo.getCalsConsumed());
                            System.out.println("key "+ ds.getKey());
                        }
                        if(dayInfo.getDate().equals(todayStdDateFormat)) {

                            //String goal = Integer.toString(3000);
                            if( metas.getConsumedCalories().equals("0")|| metas.getConsumedCalories().equals("null")) {
                                calories = 0;
                                cals_consumed = "0";
                                pStatus = 0;
                            }

                            else if( dayInfo.getCalsConsumed().equals("null") || Integer.parseInt(dayInfo.getCalsConsumed()) == 0 ){
                                pStatus = 0;
                                calories = 0;
                                cals_consumed = "1"; // Se asigna 1 para que no de error de division de 0.
                            }
                            else {
                                calories = Integer.parseInt(dayInfo.getCalsConsumed());
                                System.out.println("Total Cals consumed :" + calories);
                                pStatus = (calories * 100) / Integer.parseInt(cals_consumed);
                            }

                            pb_burned_calories.setProgress(pStatus);
                            tv_consumed_calories.setText(calories +"/"+cals_consumed);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

    }

}
