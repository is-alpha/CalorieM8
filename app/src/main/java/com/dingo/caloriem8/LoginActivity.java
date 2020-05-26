package com.dingo.caloriem8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* Falta el Forgot password */
public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnLogin, btnRegister;
    ProgressBar pbar;
    Switch rememberMe;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.login_email);
        etPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btnLogin);
        btnRegister = findViewById(R.id.login_btnSignUp);
        pbar = findViewById(R.id.login_progressBar);
        rememberMe = findViewById(R.id.login_remember);
        fAuth = FirebaseAuth.getInstance();

        pbar.setVisibility(View.GONE);

        String storedUser = readCacheFile(this, "remember");
        if(!storedUser.isEmpty()) {
            String[] info = storedUser.split("\\r?\\n");
            etEmail.setText(info[0].trim());
            etPassword.setText(info[1].trim());
            rememberMe.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

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

                if(rememberMe.isChecked()) {
                    writeCacheFile(getLoginContext(), "remember", email+"\r\n"+password);
                } else {
                    deleteTempFile(getLoginContext(), "remember");
                }

                // Auth user info
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    public void writeCacheFile(Context context, String filename, String body) {
        try {
//            File file = File.createTempFile(filename, ".tmp", context.getCacheDir());
            File file = new File(context.getFilesDir(), filename+".tmp");
            FileWriter writer = new FileWriter(file);
            writer.write(body);
            writer.flush();
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String readCacheFile(Context context, String filename) {
        StringBuilder txt = new StringBuilder();
        try{
//            File file = new File(context.getCacheDir(), filename+".tmp");
            File file = new File(context.getFilesDir(), filename+".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while((line = br.readLine()) != null) {
                txt.append(line);
                txt.append('\n');
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        return txt.toString();
    }

    public boolean deleteTempFile(Context context, String filename) {
//        File file = new File(context.getFilesDir(), filename+".tmp");
//        return file.delete();
        return context.deleteFile(filename+".tmp");
    }

    private Context getLoginContext() {
        return this;
    }
}
