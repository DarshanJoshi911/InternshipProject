package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {
    private EditText forgotEmail;
    private TextView resetText;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        //bindin views
        forgotEmail = (EditText)findViewById(R.id.forgotEmail);
        resetText = (TextView)findViewById(R.id.displayForgot);

    }


    //password reset
    public void forgot(View view) {
        String email = forgotEmail.getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            resetText.setText("Email Sent. Reset and Login.");
                        }
                        else{
                            resetText.setText("Email Not Found.");

                        }
                    }
                });



    }
}