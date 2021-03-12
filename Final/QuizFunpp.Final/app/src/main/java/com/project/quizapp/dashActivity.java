package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.quizapp.UpdateActivity;

import static com.project.quizapp.LogIn.catList;

public class dashActivity extends AppCompatActivity {
    public LinearLayout quizIcon;
    private FirebaseFirestore firebasefirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        //binding views
        quizIcon = (LinearLayout)findViewById(R.id.quizIcon);
        firebasefirestore = FirebaseFirestore.getInstance();
        loadData();

        quizIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashActivity.this, HomePage.class));
            }
        });
    }
    public void logitout(View view) {
        //logout using firebase instance
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(dashActivity.this, MainActivity.class));
        Toast.makeText(dashActivity.this, "Logged Out Successfully.",
                Toast.LENGTH_SHORT).show();
    }

    //methods to start different activities
    public void openProfile(View view) {
        startActivity(new Intent(dashActivity.this, ProfileActivity.class));
    }

    public void openReview(View view) {
        startActivity(new Intent(dashActivity.this, ReviewActivity.class));
    }

    public void openHistory(View view) {
        startActivity(new Intent(dashActivity.this, HistoryActivity.class));

    }

    public void update(View view) {
        startActivity(new Intent(dashActivity.this, UpdateActivity.class));


    }



    //Loading data on the dash screen to show on other ctivities
    private void loadData(){
        catList.clear();
        //calling firestore to fetch the data
        firebasefirestore.collection("QUIZ").document("Category")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        long count = (long)doc.get("COUNT");
                        for(int i = 1; i<= count; i++){
                            //geting values
                            String catName = doc.getString("CAT"+String.valueOf(i)+"_NAME");
                            String catID = doc.getString("CAT"+String.valueOf(i)+"_ID");
                            catList.add(new CategoryModelClass(catID,catName));
                        }
                    }
                    else{
                        Toast.makeText(dashActivity.this, "No Category Document Exists", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }else{
                    Toast.makeText(dashActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }









}