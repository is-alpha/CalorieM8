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

    interface WeightFragCallback {
        void onCompleteAction(DayInfo ant, DayInfo act);
    }
    private WeightFragCallback callback;
    private Context currContext;
    private View view;
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
        weight_act = new DayInfo();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_weight, container, false);

        /* Boton para regresar a la pestaña anterior*/
        iv_goBack = view.findViewById(R.id.iv_goBack);
        iv_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main_fragment_container, new ResultadosFragment()).commit();
            }
        });

        callback = new WeightFragCallback() {
            @Override
            public void onCompleteAction(DayInfo ant, DayInfo act) {
                weight_ant = new DayInfo(ant);
                weight_act = new DayInfo(act);

                if(weight_ant.getWeight().equals("null"))
                    weight_ant.setWeight("0");
                if(weight_act.getWeight().equals("null"))
                    weight_act.setWeight("0");

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
                    tv_message.setText("You've lost weight !");
                    tv_difWeight.setText("-" + Integer.toString(res) + " Kg");
                }
                else if(res > 0){
                    tv_message.setText("You've gained weight !");
                    tv_difWeight.setText("+" + Integer.toString(res) + " Kg");
                    tv_difWeight.setTextColor(Color.RED);
                }
                else if(res == 0)
                {
                    tv_message.setText("You weight is the same !");
                    tv_difWeight.setText(Integer.toString(res) + " Kg");
                }
            }
        };

        dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            /*No se puede asignar valor dentro de esto porque los Listeners de Firebase son async
             * se debe usar una funcion all back, variables y sus valores asignados estan limitados al scope de este listener
             * NO BORRAR COMENTARIO, PORQUE ES EN MEMORIA A T0DO EL TIEMPO QUE LE DEDIQUE A ESTA BURRADA
             * AL MENOS APRENDI ESTE DETALLE IMPORTANTE
             * REF: https://stackoverflow.com/questions/39209823/setting-variable-inside-ondatachange-in-firebase-singlevalue-listener*/
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DayInfo tmp, ant, act;
                ant = new DayInfo();
                act = new DayInfo();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    tmp = ds.getValue(DayInfo.class);
                    if(!tmp.getWeight().equals("null")) {
                        ant = new DayInfo(act);
                        act = new DayInfo(tmp);
                    }
//                    Toast.makeText(currContext, "WEIGHT = " + weight_act.getWeight(), Toast.LENGTH_SHORT).show();
                }
                callback.onCompleteAction(ant, act);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("E MMMM dd yyyy");
        date = Calendar.getInstance().getTime();
        fecha = sdf.format(date);

        tv_fecha = view.findViewById(R.id.id_fecha);
        tv_fecha.setText(fecha);

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
