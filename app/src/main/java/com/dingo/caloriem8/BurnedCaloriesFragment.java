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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;




public class BurnedCaloriesFragment extends Fragment {

    private FirebaseAuth fAuth;
    private String todayStdDateFormat;
    private String cals_Burned;
    private DatabaseReference dbRef;
    private TextView tv_burned_calories, tv_burned_calories_MBR;
    private ProgressBar pb_burned_calories, pb_burned_calories_MBR;
    private int pStatus;
    private int calories=0;
    private DayInfo dayInfo;
    private Date dateToday;
    private Meta metas;
    private User user;

    public BurnedCaloriesFragment() {
        // Required empty public constructor
    }

    public int MifflinStJeorEquation(int weight, int height, int age, String gender){
        int bmr, s;
        if(gender == "male"){
            s = 5;
        } else{
            s = -161;
        }
        bmr = (int) ((int) (10*weight) + (6.25*height) - (5*age) + s);
        return bmr;
    }

    public int getDOB(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(l1, now1);
        System.out.println("age:" + diff1.getYears() + "years");
        return diff1.getYears();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_burned_calories, container,false);

        pb_burned_calories = (ProgressBar) view.findViewById(R.id.progressBar_goals_burn_calories);
        tv_burned_calories = (TextView) view.findViewById(R.id.tv_goals_burn_calories);

        pb_burned_calories_MBR = (ProgressBar) view.findViewById(R.id.progressBar_goals_burn_calories_BMR);
        tv_burned_calories_MBR = (TextView) view.findViewById(R.id.tv_goals_burn_calories_BMR);

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

                user = dataSnapshot.child("Users").child(fAuth.getCurrentUser().getUid()).getValue(User.class);

                int BMR = MifflinStJeorEquation(Integer.parseInt(user.getWeight()), Integer.parseInt(user.getHeight()), getDOB(user.getBirth_date()), user.getGender());
                System.out.println("BMR:" + BMR);
                pb_burned_calories_MBR.setProgress(BMR);
                tv_burned_calories_MBR.setText("BMR\n" + Integer.toString(BMR)+" Kcal/Day");

                metas = dataSnapshot.child("Metas").child(fAuth.getCurrentUser().getUid()).getValue(Meta.class);
                cals_Burned = metas.getBurnedCalories();
                System.out.println("Burned Calories: "+ cals_Burned);

                Query query = dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            dayInfo = ds.getValue(DayInfo.class);
                            System.out.println("Date: " + dayInfo.getDate() + " Calories burned:"+ dayInfo.getCalsBurned());
                        }

                        if(dayInfo.getDate().equals(todayStdDateFormat)) {

                            if( metas.getBurnedCalories().equals("0")|| metas.getBurnedCalories().equals("null")) {
                                calories = 0;
                                cals_Burned = "0";
                                pStatus = 0;
                            }

                            else if( dayInfo.getCalsBurned().equals("null") || Integer.parseInt(dayInfo.getCalsBurned()) == 0 ){
                                pStatus = 0;
                                calories = 0;
                                cals_Burned = "1"; // Se asigna 1 para que no de error de division de 0.
                            }
                            else {
                                calories = Integer.parseInt(dayInfo.getCalsBurned());
                                System.out.println("Total Cals burned :" + calories);
                                pStatus = (calories * 100) / Integer.parseInt(cals_Burned);
                            }

                            String calories_status = calories +"/"+cals_Burned;
                            pb_burned_calories.setProgress(pStatus);
                            tv_burned_calories.setText(calories_status);
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
