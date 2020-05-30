package com.dingo.caloriem8;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class WeightFragment extends Fragment {
    private Context currContext;
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    String fecha;
    Date date;
    TextView tv_fecha,tv_weightAct,tv_weightAnt,tv_message,tv_difWeight;
    private DayInfo weight_ant;
    private DayInfo weight_act;
    private ImageView iv_goBack;
    int res = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        weight_ant = new DayInfo();
        weight_ant.setWeight("0");
        weight_act = new DayInfo();
        weight_act.setWeight("0");

        dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DayInfo tmp;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    tmp = ds.getValue(DayInfo.class);
                    if(!tmp.getWeight().equals("null")) {
                        weight_ant = new DayInfo(weight_act);
                        weight_act = new DayInfo(tmp);
                    }
                    Toast.makeText(currContext, "WEIGHT = " + weight_act.getWeight(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        /* Boton para regresar a la pestaña anterior*/
        iv_goBack = view.findViewById(R.id.iv_goBack);
        iv_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ResultadosFragment()).commit();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("E MMMM dd yyyy");
        date = Calendar.getInstance().getTime();
        fecha = sdf.format(date);

        tv_fecha = view.findViewById(R.id.id_fecha);
        tv_fecha.setText(fecha);

        tv_weightAct = view.findViewById(R.id.weight_act);
        tv_weightAct.setText(weight_act.getWeight() + " Kg");

        tv_weightAnt = view.findViewById(R.id.weight_ant);
        tv_weightAnt.setText(weight_ant.getWeight() + " Kg");

        //operacion de difericencia

        res = Integer.parseInt(weight_act.getWeight()) - Integer.parseInt(weight_ant.getWeight());

        tv_message = view.findViewById(R.id.message_weight);
        tv_difWeight = view.findViewById(R.id.frw_Difweight);

        if(res < 0)
        {
            tv_message.setText("You've gained weight !");
            tv_difWeight.setText("-" + Integer.toString(res) + " Kg");
        }
        else if(res > 0){
            tv_message.setText("You've lost weight !");
            tv_difWeight.setText("+" + Integer.toString(res) + " Kg");
            tv_difWeight.setTextColor(Color.RED);
        }
        else if(res == 0)
        {
            tv_message.setText("You weight is the same !");
            tv_difWeight.setText(Integer.toString(res) + " Kg");
        }

        return view;
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
