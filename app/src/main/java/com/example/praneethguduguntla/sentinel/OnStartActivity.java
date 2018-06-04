package com.example.praneethguduguntla.sentinel;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnStartActivity extends AppCompatActivity {

    private Button adminBtn, studentBtn;
    private static final int PERMISSIONS_SEND_SMS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_start);

        adminBtn = (Button) findViewById(R.id.ashish);
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        studentBtn = (Button) findViewById(R.id.studentButton);
        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SelectSchool.class);
                startActivity(i);
            }
        });
        fixPermissions();



    }


    private void fixPermissions() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE}, PERMISSIONS_SEND_SMS);
    }




}
