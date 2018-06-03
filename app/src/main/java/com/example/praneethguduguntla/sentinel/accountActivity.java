package com.example.praneethguduguntla.sentinel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class accountActivity extends AppCompatActivity {

    private ListView students;
    private DatabaseReference databaseReference;
    ArrayList phoneNumbers= new ArrayList<>();

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        students = (ListView) findViewById(R.id.students);
//        int num_users = 5;
//
//        String[] listItems = new String[num_users];
//
//        for(int i = 0; i < listItems.length; i++){
//            listItems[i] = "black and yellow";
//        }
        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("phoneNumber").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot numberDataSnapshot : dataSnapshot.getChildren()){
                    String phoneNumber = numberDataSnapshot.getKey().toString();
                    String school = numberDataSnapshot.getValue().toString();
                    if(school.equals(user.getDisplayName())){
                        phoneNumbers.add(phoneNumber);
                    }


//                    Toast.makeText(getApplicationContext(), users.toString(), Toast.LENGTH_SHORT).show();

                }

                ArrayAdapter adapter = new ArrayAdapter<String>(accountActivity.this, android.R.layout.simple_list_item_1, phoneNumbers);
                students.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
