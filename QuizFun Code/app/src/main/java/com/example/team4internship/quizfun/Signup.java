package com.example.team4internship.quizfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    EditText Email, Password;
    Button Signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);

        Signup = findViewById(R.id.signup);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String Pass = Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("Email ID is required!");
                    return;
                }

                if (Pass.length()<=6){
                    Password.setError("Password should be more than 6 characters long");
                }

                if (TextUtils.isEmpty(Pass)){
                    Password.setError("Password is required");
                    return;
                }



                else{
                    mAuth.createUserWithEmailAndPassword(email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //addUser();
                                Toast.makeText(Signup.this,"Registered Successfully!",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                            else{
                                Toast.makeText(Signup.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }


            }
        });

    }
}