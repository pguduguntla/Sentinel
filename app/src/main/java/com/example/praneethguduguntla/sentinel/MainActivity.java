package com.example.praneethguduguntla.sentinel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean isStudent = getIntent().getExtras().getBoolean("isStudent");

        ImageButton accountbutton = findViewById(R.id.accountinfo);
        ImageButton createAlert = findViewById(R.id.createAlert);

        if(isStudent){
            accountbutton.setVisibility(View.INVISIBLE);
            createAlert.setVisibility(View.INVISIBLE);
        } else {
            accountbutton.setVisibility(View.VISIBLE);
            createAlert.setVisibility(View.VISIBLE);
        }

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent accountIntent = new Intent(getApplicationContext(), accountActivity.class);
//                startActivity(accountIntent);
                Intent accountIntent = new Intent(getApplicationContext(), SelectSchool.class);
                startActivity(accountIntent);

            }
        });

        createAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alertIntent = new Intent(getApplicationContext(), SendAlert.class);
                startActivity(alertIntent);
            }
        });
    }
}
