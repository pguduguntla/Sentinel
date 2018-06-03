package com.example.praneethguduguntla.sentinel;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SelectSchool extends AppCompatActivity {

    private ListView schools;
    private DatabaseReference databaseReference;
    ArrayList users = new ArrayList<>();
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_school);



        schools = findViewById(R.id.schoolList);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userDataSnapshot : dataSnapshot.getChildren()){
                    String user = userDataSnapshot.getValue().toString();
                    users.add(user);

//                    Toast.makeText(getApplicationContext(), users.toString(), Toast.LENGTH_SHORT).show();

                }
                ArrayAdapter adapter = new ArrayAdapter<String>(SelectSchool.this, android.R.layout.simple_list_item_1, users);
                schools.setAdapter(adapter);

                schools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        index = i;
                        String schoolSelected = users.get(i).toString();
        //                Toast.makeText(getApplicationContext(), schoolSelected, Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SelectSchool.this);
                        View mView = getLayoutInflater().inflate(R.layout.phone_number, null);
                        final EditText phoneNumber = (EditText) mView.findViewById(R.id.phoneNumber);

                        Button button =  mView.findViewById(R.id.submit);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseReference.child("phoneNumber").child(phoneNumber.getText().toString()).setValue(users.get(index).toString());
                            }
                        });

                        mBuilder.setView(mView);
                        AlertDialog dialog = mBuilder.create();
                        dialog.show();

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
//        String[] listItems = new String[users.size()];
//
//        for(int i = 0; i < listItems.length; i++){
//            listItems[i] = users.get(i).toString();
//        }

//        Toast.makeText(getApplicationContext(), users.toString(), Toast.LENGTH_SHORT).show();
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(SelectSchool.this, android.R.layout.simple_list_item_1, users);
//        schools.setAdapter(adapter);

//        schools.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String schoolSelected = users.get(i).toString();
////                Toast.makeText(getApplicationContext(), schoolSelected, Toast.LENGTH_SHORT).show();
//            }
//        });

//        Toast.makeText(getApplicationContext(), users.toString(), Toast.LENGTH_SHORT).show();
    }
}
