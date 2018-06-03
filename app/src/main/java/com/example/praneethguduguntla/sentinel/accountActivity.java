package com.example.praneethguduguntla.sentinel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class accountActivity extends AppCompatActivity {

    private ListView students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        students = (ListView) findViewById(R.id.students);
        int num_users = 5;

        String[] listItems = new String[num_users];

        for(int i = 0; i < listItems.length; i++){
            listItems[i] = "black and yellow";
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        students.setAdapter(adapter);
    }
}
