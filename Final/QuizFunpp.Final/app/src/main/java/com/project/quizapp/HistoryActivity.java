package com.project.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    public RecyclerView histrecview;
    myhistadapter adapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //binding views
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, dashActivity.class));

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //uid = user.getUid();
            uid=user.getUid();
        }
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference usersRef = rootRef.child("history/");
//        Query query = usersRef.orderByChild("email").equalTo("email:" +  user.getEmail().toString());
//        Log.d("QELT", query.toString());
        histrecview = (RecyclerView)findViewById(R.id.hisrecview);
        histrecview.setLayoutManager(new LinearLayoutManager(this));
        //Calling firebase realtime database to get all the values in hist model
        ArrayList<histmodel> list = new ArrayList<>();

        FirebaseRecyclerOptions<histmodel> options = new FirebaseRecyclerOptions.Builder<histmodel>()
                .setQuery(FirebaseDatabase.getInstance("https://quizapp-6b183-default-rtdb.firebaseio.com/").getReference("history/").orderByChild("email").equalTo(user.getEmail().toString()),histmodel.class)
                .build();


//
//        FirebaseRecyclerOptions<histmodel> options = new FirebaseRecyclerOptions.Builder<histmodel>()
//                .setQuery(FirebaseDatabase.getInstance("https://quizapp-6b183-default-rtdb.firebaseio.com/").getReference().child("history/").orderByChild("email").equalTo(user.getEmail().toString()),histmodel.class)
//                .build();

//            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("history/");
//            Log.d("DATA", db.toString());

//        adapter = new myhistadapter(list);
//        view= findViewById(R.id.my_view);
//        view.setLayoutManager(manager);
//        view.setAdapter(adapter);
//        getListFromFirebase(); // Dont return anything from this method. declare it void

        adapter = new myhistadapter(options);
        histrecview.setAdapter(adapter);

    }
    //listening to the class on the start of the activity
    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    //stoping to listen on the activity stop
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }



}
