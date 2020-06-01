package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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




public class BurnedCaloriesFragment extends Fragment {

    private FirebaseAuth fAuth;
    private String todayStdDateFormat;
    private String cals_Burned;
    private DatabaseReference dbRef;
    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus;
    private int calories=0;
    private ImageView iv_goBack;
    private DayInfo dayInfo;
    private Date dateToday;
    private Meta metas;

    public BurnedCaloriesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_burned_calories, container,false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_goals_burn_calories);
        txtProgress = (TextView) view.findViewById(R.id.tv_goals_burn_calories);
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
                cals_Burned = metas.getBurnedCalories();
                System.out.println("calroia quemada :"+ cals_Burned);

                // final Query lastQuery = dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).orderByKey().limitToLast(1);
                Query query = dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            dayInfo = ds.getValue(DayInfo.class);
                            //calories += Integer.parseInt(dayInfo.getCalsBurned());
                            System.out.println("Date: " + dayInfo.getDate() + " Cals burned:"+ dayInfo.getCalsBurned());
                        }
                        if(dayInfo.getDate().equals(todayStdDateFormat)) {

                            //String goal = Integer.toString(3000);
                            if( metas.getBurnedCalories().equals("0")|| metas.getBurnedCalories().equals("null")) {
                                calories = 0;
                                cals_Burned = "0";
                                pStatus = 0;
                            }

                            else if( dayInfo.getCalsBurned().equals("null") || Integer.parseInt(dayInfo.getCalsBurned()) == 0 ){
                                pStatus = 0;
                                calories = 0;
                            }
                            else {
                                calories = Integer.parseInt(dayInfo.getCalsBurned());
                                System.out.println("Total Cals burned :" + calories);
                                pStatus = (calories * 100) / Integer.parseInt(cals_Burned);
                            }


                            progressBar.setProgress(pStatus);
                            txtProgress.setText(calories +"/"+cals_Burned);
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
