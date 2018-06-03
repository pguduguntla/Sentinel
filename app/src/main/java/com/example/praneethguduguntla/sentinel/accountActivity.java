package com.example.praneethguduguntla.sentinel;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;

public class accountActivity extends AppCompatActivity {


    private static final int PERMISSIONS_SEND_SMS = 0;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    private View mLayout;
    ArrayList<String> phoneNumbers = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

}
