package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;


import static com.project.quizapp.UpdateActivity.selected_cat_index;
import static com.project.quizapp.UpdateSetsActivity.selected_set_index;
import static com.project.quizapp.UpdateSetsActivity.setsIDs;
import static com.project.quizapp.UpdateActivity.catList;


public class UpdateQuestionsActivity extends AppCompatActivity {

    //variables
    private RecyclerView quesView;
    private Button addQB;
    public static List<UpdateQuestionsModel> quesList = new ArrayList<>();
    private UpdateQuestionsAdapter adapter;
    private FirebaseFirestore firestore;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_questions);

        //setting up toolbar
        Toolbar toolbar = findViewById(R.id.q_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //binding views
        quesView = findViewById(R.id.quest_recycler);
        addQB = findViewById(R.id.addQB);

        loadingDialog = new Dialog(UpdateQuestionsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        addQB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateQuestionsActivity.this, QuestionDetailsActivity.class);
                intent.putExtra("ACTION","ADD");
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        quesView.setLayoutManager(layoutManager);

        firestore = FirebaseFirestore.getInstance();

        loadQuestions();

    }

    //loading questions to the recyclerview
    private void loadQuestions()
    {
        quesList.clear();

        loadingDialog.show();

        firestore.collection("QUIZ").document(catList.get(selected_cat_index).getId())
                .collection(setsIDs.get(selected_set_index)).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots)
                        {
                            docList.put(doc.getId(),doc);
                        }

                        QueryDocumentSnapshot quesListDoc  = docList.get("QUESTIONS_LIST");

                        String count = quesListDoc.getString("COUNT");

                        for(int i=0; i < Integer.valueOf(count); i++)
                        {
                            String quesID = quesListDoc.getString("Q" + String.valueOf(i+1) + "_ID");

                            QueryDocumentSnapshot quesDoc = docList.get(quesID);

                            quesList.add(new UpdateQuestionsModel(
                                    quesID,
                                    quesDoc.getString("QUESTION"),
                                    quesDoc.getString("A"),
                                    quesDoc.getString("B"),
                                    quesDoc.getString("C"),
                                    quesDoc.getString("D"),
                                    Integer.valueOf(quesDoc.getString("ANSWER"))
                            ));

                        }

                        adapter = new UpdateQuestionsAdapter(quesList);
                        quesView.setAdapter(adapter);

                        loadingDialog.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateQuestionsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });

    }


    //onresume method override
    @Override
    protected void onResume() {
        super.onResume();

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}