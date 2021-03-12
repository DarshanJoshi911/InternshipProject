package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class LogIn extends AppCompatActivity {
    //variables
    public static List<CategoryModelClass> catList = new ArrayList<>();
    public static int selected_cat_index_app=0;
    EditText email, pass;
    Button login;
    //private FirebaseFirestore firebasefirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //binding values
        email = (EditText)findViewById(R.id.loginEmail);
        pass = (EditText)findViewById(R.id.loginPass);
        login = (Button) findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent = new Intent(LogIn.this, dashActivity.class);
            startActivity(intent);
        }

//        firebasefirestore = FirebaseFirestore.getInstance();
//        loadData();

    }
//    private void loadData(){
//        catList.clear();
//        firebasefirestore.collection("QUIZ").document("Category")
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//
//                    DocumentSnapshot doc = task.getResult();
//                    if(doc.exists()){
//                        long count = (long)doc.get("COUNT");
//                        for(int i = 1; i<= count; i++){
//                            String catName = doc.getString("CAT"+String.valueOf(i)+"_NAME");
//                            String catID = doc.getString("CAT"+String.valueOf(i)+"_ID");
//                            catList.add(new CategoryModelClass(catID,catName));
//                        }
//                    }
//                    else{
//                        Toast.makeText(LogIn.this, "No Category Document Exists", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//
//                }else{
//                    Toast.makeText(LogIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }

    public void login(View view) {

        String logEmail = email.getText().toString();
        String logPass = pass.getText().toString();


        mAuth.signInWithEmailAndPassword(logEmail, logPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LogIn.this, "Logging You In...",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIn.this, dashActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });


    }
    //forget activity intent
    public void forgotClicked(View view) {
        startActivity(new Intent(LogIn.this, ForgotActivity.class));
    }
}