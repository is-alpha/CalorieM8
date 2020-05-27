package com.dingo.caloriem8;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeightFragment extends Fragment {

    String fecha;
    Date date;
    TextView tv_fecha,tv_weightAct,tv_weightAnt,tv_message,tv_difWeight;
    DayInfo weight_ant = new DayInfo();
    DayInfo weight_act = new DayInfo();
    int res = 0;

    public WeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        SimpleDateFormat sdf = new SimpleDateFormat("E MMMM dd yyyy");
        date = Calendar.getInstance().getTime();
        fecha = sdf.format(date);

        tv_fecha = view.findViewById(R.id.id_fecha);
        tv_fecha.setText(fecha);

        //variables temporales QUE SE VAN A BORRRA
        weight_act.setWeight(Integer.toString(120));
        weight_ant.setWeight(Integer.toString(110));

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

}
