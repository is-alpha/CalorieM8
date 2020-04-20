package com.dingo.caloriem8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class PersonalDataActivity extends AppCompatActivity {
    EditText et_date;
    DatePickerDialog.OnDateSetListener dp_dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        et_date = findViewById(R.id.et_reg_odb);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(PersonalDataActivity.this,
                        R.style.Theme_AppCompat_Light_Dialog,
                        dp_dateSetListener,
                        year, month, day);
                dialog.getWindow();
                dialog.show();
            }
        });

        dp_dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/"+ dayOfMonth + "/" + year;
                et_date.setText(date);
            }
        };
    }
}
