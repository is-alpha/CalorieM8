package com.dingo.caloriem8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* AGREGAR LO OTRO PESO, ALTURA, ETC */
public class RegisterActivity extends AppCompatActivity {
    User user;
    EditText etUsername, etEmail, etPassword;
    Button btnRegister, btnLogin;
    ProgressBar pbar;

    FirebaseAuth fAuth;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = new User();

        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        etUsername = findViewById(R.id.reg_username);
        etEmail = findViewById(R.id.reg_email);
        etPassword = findViewById(R.id.reg_password);
        btnRegister = findViewById(R.id.reg_btnSignUp);
        btnLogin = findViewById(R.id.reg_btnLogin);
        pbar = findViewById(R.id.reg_progressBar);

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                final boolean acc_Complete = false;

                if(TextUtils.isEmpty(email)) {
                    etEmail.setError("Invalid Email");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    etPassword.setError("Invalid Password");
                    return;
                }

                if(password.length() < 6) {
                    etPassword.setError("Password must be at least 6 chars long");
                    return;
                }

                pbar.setVisibility(View.VISIBLE);

                // register user in firebase auth and realtime db
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Map<String, Object> new_data = new HashMap<>();
                            new_data.put("username", username);
                            new_data.put("email", email);
                            new_data.put("password", password);
                            new_data.put("acc_complete", acc_Complete);

                            user.setUserDisplayname(username);
                            user.setUserEmail(email);
                            user.setUserPassword(password);
                            user.setAccComplete(acc_Complete);

                            String id = fAuth.getCurrentUser().getUid();
                            dbRef.child("Users").child(id).setValue(new_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if(task2.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), PersonalDataActivity.class);
                                        intent.putExtra("User", user);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Error: " + task2.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        pbar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
