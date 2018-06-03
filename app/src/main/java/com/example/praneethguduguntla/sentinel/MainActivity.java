package com.example.praneethguduguntla.sentinel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText userName, emailField, userPassword;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       userName = (EditText) findViewById(R.id.schoolName);
       emailField = (EditText) findViewById(R.id.email);
       userPassword = (EditText) findViewById(R.id.password);

       mAuth = FirebaseAuth.getInstance();

    }





}
