package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText email,pass,name;
    Button button;
    //TextView login;
    private FirebaseAuth mAuth;
//    FirebaseDatabase rootNode;
//    DatabaseReference reference;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding views
        email = (EditText) findViewById(R.id.loginEmail);
        pass = (EditText) findViewById(R.id.loginPass);
        button = (Button) findViewById(R.id.loginButton);
        //onclick listener on the button to log in
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Data is fetched on the click from both the edittexts
                String em = email.getText().toString();
                String passid = pass.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                //Registering the user using firebase
                mAuth.createUserWithEmailAndPassword(em, passid)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //If the user is successfully created
                                    Toast.makeText(MainActivity.this, "User Added",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //If the user account creation is failed
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
    //Getter setter methods on the click
    public void loginClicked(View view) {
        startActivity(new Intent(MainActivity.this, LogIn.class));
    }

    public void forgotClicked(View view) {
        startActivity(new Intent(MainActivity.this, ForgotActivity.class));
    }
}