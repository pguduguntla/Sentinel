package com.example.praneethguduguntla.sentinel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    FirebaseUser currSchoolUser;

    ImageView safetyCircle;
    ImageView indicator;
    TextView textStatus;


    DatabaseReference mDatabase;



    String currSchool;

    boolean schoolSafe = true;

    ListView presets;

    ImageView img;

    ImageView reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ImageButton accountbutton = findViewById(R.id.accountinfo);
        ImageButton createAlert = findViewById(R.id.createAlert);

        textStatus = (TextView)findViewById(R.id.textStatus);
        textStatus.setGravity(Gravity.CENTER);

        presets = (ListView)findViewById(R.id.presets);

        if(FirebaseAuth.getInstance().getCurrentUser() != null) {

            currSchoolUser = FirebaseAuth.getInstance().getCurrentUser();

            currSchool = currSchoolUser.getDisplayName();

        } else {
            currSchool =  getIntent().getExtras().getString("schoolName");

            createAlert.setVisibility(View.INVISIBLE);
            accountbutton.setVisibility(View.INVISIBLE);

        }

        img = (ImageView)findViewById(R.id.map);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 boolean hasAlerts = true;
                 if(!dataSnapshot.child("Map Users").exists()) {
                     hasAlerts = false;
                 } else if(!dataSnapshot.child("Map Users").child(currSchool + "").exists()) {
                     hasAlerts = false;
                 }

                 textStatus.setGravity(Gravity.CENTER);

                 if(hasAlerts) {

                     for (DataSnapshot d : dataSnapshot.child("Map Users").child(currSchool).getChildren()) {

                         System.out.println(d.child("x").getValue() + " " + d.child("y").getValue());
                         ImageView imageView = new ImageView(getApplicationContext());

                         boolean childIsSafe = Boolean.parseBoolean((String) d.child("safe").getValue());

                         if (!childIsSafe) {
                             imageView.setImageResource(R.drawable.warning);
                             schoolSafe = false;
                             safetyCircle = (ImageView)findViewById(R.id.safetyCircle);
                             safetyCircle.setImageResource(R.drawable.redcircle);
                             indicator = (ImageView)findViewById(R.id.safetyIndicator);
                             indicator.setImageResource(R.drawable.checkplease);
                             textStatus.setGravity(Gravity.CENTER);
                             textStatus.setText("Danger on Campus");

                         } else {
                             imageView.setImageResource(R.drawable.safe);
                         }

                         ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.layout);
                         ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(61, 100);
                         // use the coordinates for whatever
                         constraintLayout.addView(imageView, layoutParams);

                         float x = Float.parseFloat((String) d.child("x").getValue());
                         float y = Float.parseFloat((String) d.child("y").getValue());


                         imageView.setX(x + img.getX() - 31);
                         imageView.setY(y + img.getY() - 31);


                     }
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });


        if(!schoolSafe) {

        }

        reload = (ImageView)findViewById(R.id.reload);

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("schoolName" , currSchool);
                startActivity(i);
            }
        });



        //Boolean isStudent = getIntent().getExtras().getBoolean("isStudent");



        /*if(isStudent){
            accountbutton.setVisibility(View.INVISIBLE);
            createAlert.setVisibility(View.INVISIBLE);
        } else {
            accountbutton.setVisibility(View.VISIBLE);
            createAlert.setVisibility(View.VISIBLE);
        }*/

        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent accountIntent = new Intent(getApplicationContext(), accountActivity.class);
//                startActivity(accountIntent);
                Intent accountIntent = new Intent(getApplicationContext(), accountActivity.class);
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
