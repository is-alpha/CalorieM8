package com.dingo.caloriem8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumedCaloriesFragment extends Fragment {

    private static final String EXTRA_INFO_ID = "infoId" ;
    private int infoId;
    private FirebaseAuth fAuth;
    private String todayStdDateFormat;
    private DatabaseReference dbRef;
    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus;
    private int calories=0;
    private Handler handler = new Handler();
    private ImageView iv_goBack;
    private DayInfo dayInfo;
    private Date dateToday;

    public ConsumedCaloriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null){

        }else {
            infoId = savedInstanceState.getInt(EXTRA_INFO_ID);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consumed_calories, container, false);

        iv_goBack = view.findViewById(R.id.iv_goBack);

        iv_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ResultadosFragment()).commit();
            }
        });
        txtProgress = view.findViewById(R.id.tv_goals_consumed_calories);
        progressBar = view.findViewById(R.id.progressBar_goals_consumed_calories);

        /**Database instances*/
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();


        dateToday = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        todayStdDateFormat = df.format(dateToday);

        Query query = dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    dayInfo = ds.getValue(DayInfo.class);
                    System.out.println("Date" + dayInfo.getDate() + "Calories consumed:" + dayInfo.getCalsConsumed());
                    if (dayInfo.getDate().equals(todayStdDateFormat)){
                        String goal = Integer.toString(1500);

                        calories = Integer.parseInt(dayInfo.getCalsConsumed());
                        System.out.println("Total calories consumed:" + calories);
                        pStatus = (calories*100)/1500;

                        progressBar.setProgress(pStatus);
                        txtProgress.setText(goal + "/" + calories);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return view;
    }
}
