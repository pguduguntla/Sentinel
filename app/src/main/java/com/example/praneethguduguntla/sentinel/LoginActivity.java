package com.example.praneethguduguntla.sentinel;

import android.content.Intent;
import android.net.Uri;
import android.sax.StartElementListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText userName, emailField, userPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userName = (EditText) findViewById(R.id.schoolName);
        emailField = (EditText) findViewById(R.id.email);
        userPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user!=null) {
           Intent i = new Intent(getApplicationContext(), MainActivity.class);
           startActivity(i);
        } else {
            Button logInButton = (Button) findViewById(R.id.signUpButton);
            logInButton.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    createUser(emailField.getText().toString(), userPassword.getText().toString(), userName.getText().toString());

                }
            });
        }



    }

    int i = 0;

    public void createUser(String email, String password, final String uName) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference();


                            myRef.child("users").child("" + i++).setValue(uName);


                            // Intent i = new Intent(LogInActivity.this, WeekViewTest.class);
                            //startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });


    }
}



