package com.dingo.caloriem8;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private User user;
    private FirebaseAuth fAuth;
    private DatabaseReference dbRef;
    private Context currContext;

    private EditText et_email;
    private EditText et_username;
    private Spinner sp_gender;
    private EditText et_height;
    private EditText et_weight;
    private EditText et_date;
    private DatePickerDialog.OnDateSetListener dp_dateSetListener;
    private Button btnSubmit;
    private ProgressBar pbar;

    ArrayAdapter<CharSequence> gAdapter;
    private String gender;
    private String birthdate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        et_email = view.findViewById(R.id.frp_email);
        et_email.setKeyListener(null);
        et_username = view.findViewById(R.id.frp_username);
        et_username.setKeyListener(null);

        pbar = view.findViewById(R.id.frp_pdata_pbar);
        btnSubmit = view.findViewById(R.id.frp_btnSubmit);

        sp_gender = view.findViewById(R.id.frp_inputGender);
        gAdapter = ArrayAdapter.createFromResource(currContext, R.array.gender_options, android.R.layout.simple_spinner_item);
        gAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gender.setAdapter(gAdapter);
        sp_gender.setOnItemSelectedListener(this);

        et_height = view.findViewById(R.id.frp_inputHeight);
        et_weight = view.findViewById(R.id.frp_inputWeight);

        et_date = view.findViewById(R.id.et_frp_odb);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(currContext,
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
                    et_date.setError("Invalid birth date");
                    return;
                }

                //pbar.setVisibility(View.VISIBLE);

                user.setGender(gender);
                user.setHeight(height);
                user.setWeight(weight);
                user.setBirth_date(birthdate);

                //Update user with new info
                User updatedUser = new User(user.getId(), user.getEmail(), user.getPassword(), user.getDisplayName(), user.getGender(), user.getHeight(), user.getWeight(), user.getBirth_date(), "true");
                dbRef.child("Users").child(fAuth.getCurrentUser().getUid().toString()).setValue(updatedUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(currContext, "Information Updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

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

    @Override
    public void onStart() {
        super.onStart();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child("Users").child(fAuth.getCurrentUser().getUid()).getValue(User.class);
                et_email.setText(user.getEmail());
                et_username.setText(user.getDisplayName());
                if(user.getAccComplete().equals("true")) {
                    sp_gender.setSelection(gAdapter.getPosition(user.getGender()));
                    et_height.setText(user.getHeight());
                    et_weight.setText(user.getWeight());
                    birthdate = user.getBirth_date();
                    et_date.setText(birthdate);
                } else {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = df.format(Calendar.getInstance().getTime());
                    DayInfo first = new DayInfo(strDate, "null", "null");
                    dbRef.child("DayInfo").child(fAuth.getCurrentUser().getUid()).setValue(first);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
