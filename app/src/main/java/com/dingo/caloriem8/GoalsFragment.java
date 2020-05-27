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
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.dingo.caloriem8.ManDailyCaloriesFragment.EXTRA_INFO_ID;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GoalsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoalsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int infoId;
    private Context currContext;
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;

    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus;
    private Handler handler = new Handler();
    private ImageView iv_goBack;

    public GoalsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoalsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoalsFragment newInstance(String param1, String param2) {
        GoalsFragment fragment = new GoalsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if ( savedInstanceState != null) {
        }else{

        }
        View view = inflater.inflate(R.layout.fragment_goals, container,false);





        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_goals_burn_calories);
        txtProgress = (TextView) view.findViewById(R.id.tv_goals_burn_calories);
        iv_goBack = view.findViewById(R.id.iv_goBack);

        iv_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ResultadosFragment()).commit();
            }
        });

        /*Esto se quitara despues, son con fines visuales de como funciona
        el progressbar
        * */
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child("DayInfo").child(fAuth.getCurrentUser().getUid()).child("1");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DayInfo dayInfo  = dataSnapshot.getValue(DayInfo.class);

                System.out.println("Cals burned:"+ dayInfo.getCalsBurned());
                System.out.println("Avg sleep:"+ dayInfo.getAvgSleep());
                System.out.println("Cals consumed:"+ dayInfo.getCalsConsumed());
                System.out.println("date:"+ dayInfo.getDate());
                String goal = Integer.toString(1000);

                pStatus = (921*100)/1000;

                progressBar.setProgress(pStatus);
                txtProgress.setText(goal+"/"+921);
                if(pStatus >= 100){
                    System.out.println("Meta cumplida");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}
