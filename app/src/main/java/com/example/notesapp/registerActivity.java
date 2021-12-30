package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {
    private EditText regEmail,regPassword;
    private Button registerBtn;
    private TextView toLogin;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regEmail = (EditText) findViewById(R.id.remail);
        regPassword = (EditText) findViewById(R.id.rpassword);
        registerBtn = (Button) findViewById(R.id.btn_register);
        toLogin = (TextView) findViewById(R.id.toLogin);

        mAuth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this,LoginActivity.class));
            }
        });

    }

    private void createUser(){
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            regEmail.setError("Email cannot be empty");
            regEmail.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            regPassword.setError(("Password cannot be empty"));
            regPassword.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(registerActivity.this, "User account successfully created.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(registerActivity.this,LoginActivity.class));
                    }else{
                        Toast.makeText(registerActivity.this, "Registration has failed. Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}