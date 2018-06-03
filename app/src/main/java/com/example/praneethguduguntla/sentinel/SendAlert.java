package com.example.praneethguduguntla.sentinel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SendAlert extends AppCompatActivity {
    int timeTouch = 0;
    private float[] lastTouchXY = new float[2];
    private DatabaseReference mDatabase;
    private ArrayList<String> phones = new ArrayList<String>();
    String currSchool = "Cupertino High School";

    ImageView warn;

    boolean isSafe = false;

    ImageView safe;
    ImageView img;
    TextView screen;
    Button clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        warn = (ImageView)findViewById(R.id.warning);
        safe = (ImageView)findViewById(R.id.safe);

        warn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSafe = false;
            }
        });

        safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSafe = true;
            }
        });
//
        final EditText message = findViewById(R.id.messageText);
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference();


        dr.child("phoneNumbers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot phoneDataSnapshot : dataSnapshot.getChildren()){
                    phones.add(phoneDataSnapshot.getKey().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ImageButton sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAll send = new SendAll(phones, message.getText().toString());
                send.sendAll();
            }
        });



        img = (ImageView)findViewById(R.id.map);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.child("Map Users").child(currSchool).getChildren()) {
                    /*double x = (double)d.child("x").getValue();
                    double y = (double)d.child("y").getValue();*/
                    System.out.println(d.child("x").getValue() + " " + d.child("y").getValue());
                    ImageView imageView = new ImageView(getApplicationContext());

                    boolean childIsSafe = Boolean.parseBoolean((String)d.child("safe").getValue());

                    if(!childIsSafe) {
                        imageView.setImageResource(R.drawable.warning);
                    } else {
                        imageView.setImageResource(R.drawable.safe);
                    }

                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(70, 70);
                    // use the coordinates for whatever
                    relativeLayout.addView(imageView, layoutParams);

                    float x = Float.parseFloat((String)d.child("x").getValue());
                    float y = Float.parseFloat((String)d.child("y").getValue());




                    imageView.setX(x + img.getX() - 35);
                    imageView.setY(y + img.getY() - 35);
                    timeTouch++;


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        clear = (Button)findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Map Users").child(currSchool + "").setValue("");
                Intent i = new Intent(getApplicationContext(), SendAlert.class);
                startActivity(i);
            }
        });

        screen = (TextView)findViewById(R.id.border);
        //img.setOnClickListener(clickListener);
        img.setOnClickListener(clickListener);
        img.setOnTouchListener(touchListener);

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // retrieve the stored coordinates
            float x = lastTouchXY[0];
            float y = lastTouchXY[1];


            /*img.setX(x);
            img.setY(y);*/
            ImageView imageView = new ImageView(getApplicationContext());
            if(isSafe) {
                imageView.setImageResource(R.drawable.safe);
            } else {
                imageView.setImageResource(R.drawable.warning);
            }

            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(70, 70);
            // use the coordinates for whatever
            relativeLayout.addView(imageView, layoutParams);



            imageView.setX(x/* - imageView.getMaxWidth() / 2*/ + v.getX() - 35);
            imageView.setY(y /*- imageView.getMaxHeight() / 2)*/ + v.getY() - 35);
            mDatabase.child("Map Users").child(currSchool).child("Point " + timeTouch).child("x").setValue((double)x + "f");
            mDatabase.child("Map Users").child(currSchool).child("Point " + timeTouch).child("y").setValue((double)y + "f");
            mDatabase.child("Map Users").child(currSchool).child("Point " + timeTouch).child("safe").setValue(isSafe + "");






        }
    };

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                lastTouchXY[0] = event.getX();
//                lastTouchXY[1] = event.getY();
//                img.setX(/*lastTouchXY[0]*/0);
//                img.setY(/*lastTouchXY[1]*/0);
//                Toast.makeText(getApplicationContext(), lastTouchXY[0] + " " + lastTouchXY[1], Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
//        return false;
//
//    }


    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    lastTouchXY[0] = event.getX();
                    lastTouchXY[1] = event.getY();
                    timeTouch++;

                    break;
                default:
                    break;
            }
            return false;

        }
    };




}
