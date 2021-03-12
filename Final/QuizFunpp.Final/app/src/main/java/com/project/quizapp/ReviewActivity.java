package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewActivity extends AppCompatActivity {
    DatabaseReference ref;
    String userEmail;
    EditText text;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

            //Setting up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add a Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewActivity.this, dashActivity.class));

            }
        });


        //Firebase database reference of reviews
        ref = FirebaseDatabase.getInstance("https://quizapp-6b183-default-rtdb.firebaseio.com/").getReference().child("reviewList/");

        text = findViewById(R.id.reviewtext);
        send = findViewById(R.id.sendReview);

        //getting the useremail
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //getting the user email
            userEmail = user.getEmail();
        } else {
            // There is no user email
        }
        //send button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rw = text.getText().toString();
                String key = ref.push().getKey();
                ref.child(key).child("Name").setValue(userEmail.toString());
                ref.child(key).child("Review").setValue(rw);

                Toast.makeText(ReviewActivity.this, "Your Review has been added. ", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void checkReviews(View view) {
        startActivity(new Intent(ReviewActivity.this, ReviewListActivity.class));
    }


}