package com.example.team4internship.quizfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.logging.Handler;

public class Login extends AppCompatActivity {

    EditText Email, Password;

    TextView ForgetPass;
    FirebaseAuth Data;
    Button Login;
    private FirebaseAuth mAuth;

    public static final int WAIT_TIME = 3 * 60 * 1000;

    private int loginAttempts = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Data = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);

        Login = findViewById(R.id.login);
        ForgetPass = findViewById(R.id.forgotpass);



        Login.setOnClickListener(new View.OnClickListener() {
            android.content.Intent Intent;
            @Override
            public void onClick(View view) {

                String AuthEmail, AuthPassword;
                AuthEmail = Email.getText().toString().trim();
                AuthPassword = Password.getText().toString().trim();

                Data.signInWithEmailAndPassword(AuthEmail,AuthPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(loginAttempts == 0) {

                            Toast.makeText(Login.this, "You have entered wrong password so many times. PLease restart the app to log in again.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Successfully Login",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                        else{
                            loginAttempts--;
                            Toast.makeText(Login.this, "Error!"+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

        ForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email;

                email=Email.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Email.setError("Field should not be empty!");
                }
                else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Login.this,"Password reset Link has been mailed to your email address.",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}