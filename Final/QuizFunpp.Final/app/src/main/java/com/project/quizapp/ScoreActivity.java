package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.project.quizapp.LogIn.catList;
import static com.project.quizapp.LogIn.selected_cat_index_app;

public class ScoreActivity extends AppCompatActivity {
    private TextView score;
    private Button done, save;
    String score_st, userEmail;
    DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        //view bindings
        score = findViewById(R.id.sa_score);
        done = findViewById(R.id.sa_done);
        save = findViewById(R.id.save_quiz);
        //score from the intent
        String score_st = getIntent().getStringExtra("SCORE");
        String SetNo = getIntent().getStringExtra("SET");

        String set = String.valueOf(Integer.parseInt(SetNo) +1);

        //checking for the user email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //getting the user email
            userEmail = user.getEmail();
        } else {
            // There is no user email
        }

        score.setText(score_st);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScoreActivity.this.startActivity(new Intent(ScoreActivity.this, HomePage.class));
                ScoreActivity.this.finish();
            }
        });
        //saving to the realtime database
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db= FirebaseDatabase.getInstance("https://quizapp-6b183-default-rtdb.firebaseio.com/").getReference().child("history/");



                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String User = user.getUid();

                final Map<String,Object> data=new HashMap<>();
                data.put("COUNT", 0);
                String COUN = "0";

                final Map<String,Object> map=new HashMap<>();
                map.put("set", set);
                map.put("email", userEmail.toString());
                map.put("cat",catList.get(selected_cat_index_app).getName());
                map.put("score",score_st);
                //map.put("uname",uname.getText().toString());

                db.child(User).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        db.child(db.push().getKey()).updateChildren(map);


//                        if(dataSnapshot.exists()){
//                            if(dataSnapshot.child("COUNT").exists()){
//                                String count = dataSnapshot.child("COUNT").getValue().toString();
//                                int counts = Integer.parseInt(count);
//                                db.child(User).child(String.valueOf(counts+1)).updateChildren(map);
//                                db.child(User).child("COUNT").setValue(String.valueOf(counts+1));
//                            }
//                            else{
//                                db.child(User).updateChildren(data);
//                                db.child(User).child(dataSnapshot.child("COUNT").getValue().toString()).updateChildren(map);
//
//                            }
//
//                        }
//                                //Toast.makeText(ScoreActivity.this, "COUNT DOES NOT EXISTS", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }
        });

    }

}