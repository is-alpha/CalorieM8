package com.dingo.caloriem8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PersonalDataActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    User user;
    Spinner sp_gender;
    EditText et_height;
    EditText et_weight;
    EditText et_date;
    EditText et_birthdate;
    DatePickerDialog.OnDateSetListener dp_dateSetListener;
    Button btnSubmit;
    ProgressBar pbar;

    String gender;
    String birthdate;

    FirebaseAuth fAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        user = (User) getIntent().getSerializableExtra("User");

        pbar = findViewById(R.id.reg_pdata_pbar);
        btnSubmit = findViewById(R.id.reg_btnSubmit);

        sp_gender = findViewById(R.id.reg_inputGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(adapter);
        sp_gender.setOnItemSelectedListener(this);

        et_height = findViewById(R.id.reg_inputHeight);
        et_weight = findViewById(R.id.reg_inputWeight);

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
                birthdate = month + "/" + dayOfMonth + "/" + year;
                et_date.setText(birthdate);
            }
        };

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String height = et_height.getText().toString().trim();
                final String weight = et_weight.getText().toString().trim();

                if(gender == null) {
                    return;
                }

                if(TextUtils.isEmpty(height)) {
                    et_height.setError("Invalid height");
                    return;
                }
                try {
                    Float.parseFloat(height);
                }catch (NumberFormatException e) {
                    et_height.setError("Invalid height");
                    return;
                }

                if(TextUtils.isEmpty(weight)) {
                    et_height.setError("Invalid weight");
                    return;
                }
                try {
                    Float.parseFloat(weight);
                }catch (NumberFormatException e) {
                    et_height.setError("Invalid weight");
                    return;
                }

                if(TextUtils.isEmpty(birthdate)) {
                    //et_height.setError("Invalid birth date");
                    et_date.setError("Invalid birth date");
                    return;
                }

                pbar.setVisibility(View.VISIBLE);

                user.setGender(gender);
                user.setHeight(height);
                user.setWeight(weight);
                user.setBirth_date(birthdate);
                user.setAccComplete("true");

                String id = fAuth.getCurrentUser().getUid();

                dbRef.child("Users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> extraDataTask) {
                        if(extraDataTask.isSuccessful()) {
                            Toast.makeText(PersonalDataActivity.this, "Profile Complete!", Toast.LENGTH_SHORT).show();
                            fAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(PersonalDataActivity.this, "Error: " + extraDataTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), gender, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
