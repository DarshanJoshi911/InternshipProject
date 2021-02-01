package com.example.team4internship.quizfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    Button home, review, history, profile;
    Button squiz, saved, myquiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home=findViewById(R.id.home);
        review=findViewById(R.id.review);
        history=findViewById(R.id.history);
        profile=findViewById(R.id.profile);
        squiz=findViewById(R.id.squiz);
        saved=findViewById(R.id.saved);
        myquiz=findViewById(R.id.myquiz);

    }
}