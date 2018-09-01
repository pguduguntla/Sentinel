package com.example.praneethguduguntla.sentinel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendAlert extends AppCompatActivity {
    int timeTouch = 0;
    private float[] lastTouchXY = new float[2];
    private DatabaseReference mDatabase;
    private ArrayList<String> phones = new ArrayList<String>();
    ImageView warn;
    String currSchool;
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    TextView border;

    String TAG = "----->";

    boolean isSafe = false;



    ImageView safe;
    ImageView img;
    TextView screen;
    Button clear;

    ListView presets;
    TextView showPresets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);




        border = (TextView)findViewById(R.id.border);
        border.setFocusableInTouchMode(true);
        border.requestFocus();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        warn = (ImageView)findViewById(R.id.warning);
        safe = (ImageView)findViewById(R.id.safe);

        currSchool = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        presets = (ListView)findViewById(R.id.presets);
        presets.setVisibility(View.INVISIBLE);
        presets.setBackgroundColor(getResources().getColor(R.color.white));

        String[] options = new String[]{"Threat on campus!", "All clear!"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, options);
        presets.setAdapter(adapter);
        showPresets = (TextView)findViewById(R.id.showPresets);
        showPresets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presets.getVisibility() == View.INVISIBLE) {
                    presets.setVisibility(View.VISIBLE);
                    showPresets.setBackground(getResources().getDrawable(R.drawable.dropdownopen));
                } else {
                    presets.setVisibility(View.INVISIBLE);
                    showPresets.setBackground(getResources().getDrawable(R.drawable.dropdown));
                }
            }
        });
        presets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(view != null) {
                    String message = ((TextView)view).getText().toString();
                    for (int i = 0; i < phones.size(); i++) {
                        try {
                            //Toast.makeText(getApplicationContext(), phones.get(i), Toast.LENGTH_SHORT).show();
                            SmsManager smsManager = SmsManager.getDefault();
                            //Toast.makeText(getApplicationContext(), message.getText().toString(), Toast.LENGTH_SHORT).show();
                            smsManager.sendTextMessage(phones.get(i), null, message, null, null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Toast.makeText(getApplicationContext(), currSchool, Toast.LENGTH_SHORT);


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


        dr.child("phoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot phoneDataSnapshot : dataSnapshot.getChildren()){
                    if(phoneDataSnapshot.getValue().equals(currSchool)) {
                        phones.add(phoneDataSnapshot.getKey().toString());
                        //Toast.makeText(getApplicationContext(), phoneDataSnapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
                    }
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


                for(int i = 0; i < phones.size(); i++){
                    try {
                        //Toast.makeText(getApplicationContext(), phones.get(i), Toast.LENGTH_SHORT).show();
                        SmsManager smsManager = SmsManager.getDefault();
                        //Toast.makeText(getApplicationContext(), message.getText().toString(), Toast.LENGTH_SHORT).show();
                        smsManager.sendTextMessage(phones.get(i), null, message.getText().toString(), null, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }




            }
        });




        img = (ImageView)findViewById(R.id.map);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean hasAlerts = true;
                if(!dataSnapshot.child("Map Users").exists()) {
                    hasAlerts = false;
                } else if(!dataSnapshot.child("Map Users").child(currSchool + "").exists()) {
                    hasAlerts = false;
                }

                if(hasAlerts) {
                    for (DataSnapshot d : dataSnapshot.child("Map Users").child(currSchool).getChildren()) {
                        /*double x = (double)d.child("x").getValue();
                        double y = (double)d.child("y").getValue();*/
                        System.out.println(d.child("x").getValue() + " " + d.child("y").getValue());
                        ImageView imageView = new ImageView(getApplicationContext());

                        boolean childIsSafe = Boolean.parseBoolean((String) d.child("safe").getValue());

                        if (!childIsSafe) {
                            imageView.setImageResource(R.drawable.warning);
                        } else {
                            imageView.setImageResource(R.drawable.safe);
                        }

                        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout);
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(70, 70);
                        // use the coordinates for whatever
                        relativeLayout.addView(imageView, layoutParams);

                        float x = Float.parseFloat((String) d.child("x").getValue());
                        float y = Float.parseFloat((String) d.child("y").getValue());


                        imageView.setX(x + img.getX() - 35);
                        imageView.setY(y + img.getY() - 35);
                        timeTouch++;


                    }
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

        //screen = (TextView)findViewById(R.id.border);
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
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(62, 100);
            // use the coordinates for whatever
            relativeLayout.addView(imageView, layoutParams);

            //Toast.makeText(getApplicationContext(), currSchool, Toast.LENGTH_SHORT).show();


            imageView.setX(x/* - imageView.getMaxWidth() / 2*/ + v.getX() - 31);
            imageView.setY(y /*- imageView.getMaxHeight() / 2)*/ + v.getY() - 31);
            mDatabase.child("Map Users").child(currSchool).child("Point " + timeTouch).child("x").setValue((double)x + "f");
            mDatabase.child("Map Users").child(currSchool).child("Point " + timeTouch).child("y").setValue((double)y + "f");
            mDatabase.child("Map Users").child(currSchool).child("Point " + timeTouch).child("safe").setValue(isSafe + "");


            fs.collection("Schools").document(currSchool).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            float x = lastTouchXY[0];
                            float y = lastTouchXY[1];
                            DocumentSnapshot doc = task.getResult();
                            if(doc.exists()) {
                               /* ArrayList<ArrayList<Float>> points = (ArrayList<ArrayList<Float>>)doc.get("Points");
                                Log.e(TAG, points + "");
                                ArrayList<Float> currPoint = new ArrayList<Float>(Arrays.asList(x, y, isSafe ? 1f : 0f));
                                points.add(currPoint);
                                Log.e(TAG, points.get(0).toString() + "");
                                fs.collection("Schools").document(currSchool).update("Points", points).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, e + "");
                                    }
                                });*/

                            }
                        }
                    });






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
