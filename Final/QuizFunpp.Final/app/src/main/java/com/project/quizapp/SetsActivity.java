package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Sets;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.project.quizapp.LogIn.catList;
import static com.project.quizapp.LogIn.selected_cat_index_app;
import static com.project.quizapp.UpdateActivity.selected_cat_index;

public class SetsActivity extends AppCompatActivity {
    private GridView sets_grid;
    private FirebaseFirestore firestore;
    //public static int category_id;

    public static List<String> setsIDs = new ArrayList<>();
    public Dialog loadingDialog;
    public static String CategotyTitle, ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        Toolbar toolbar = findViewById(R.id.set_toolbar);
        setSupportActionBar(toolbar);
        CategotyTitle = getIntent().getStringExtra("NAME");
        ID = getIntent().getStringExtra("ID");
//        category_id = getIntent().getIntExtra("ID", 1);

//        getSupportActionBar().setTitle(catList.get(selected_cat_index_app).getName());
        getSupportActionBar().setTitle(CategotyTitle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sets_grid = findViewById(R.id.sets_gridview);

        loadingDialog = new Dialog(SetsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progressbar);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_background);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        TextView setTitle = (TextView)findViewById(R.id.setName);
        setTitle.setText(CategotyTitle + "'s Quiz Sets");

        firestore = FirebaseFirestore.getInstance();
        loadSets();

    }

    public void loadSets(){



        setsIDs.clear();


//        loadingDialog.show();

        firestore.collection("QUIZ").document(ID)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                long noOfSets = (long)documentSnapshot.get("SETS");

                for(int i=1; i <= noOfSets; i++)
                {
                    setsIDs.add(documentSnapshot.getString("SET" + String.valueOf(i) + "_ID"));
                }

//                UpdateActivity.catList.get(selected_cat_index).setSetCounter(documentSnapshot.getString("COUNTER"));
//                UpdateActivity.catList.get(selected_cat_index).setNoOfSets(String.valueOf(noOfSets));

//                adapter = new UpdateSetAdapter(setsIDs);
//                setsView.setAdapter(adapter);


                        SetsAdapter adapter = new SetsAdapter(setsIDs.size());
                        sets_grid.setAdapter(adapter);

                loadingDialog.dismiss();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SetsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });












//        firestore.collection("QUIZ").document("CAT"+String.valueOf(category_id))
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//
//                    DocumentSnapshot doc = task.getResult();
//                    if(doc.exists()){
//                        long sets = (long)doc.get("SETS");
//
//                        SetsAdapter adapter = new SetsAdapter((int)sets);
//                        sets_grid.setAdapter(adapter);
//                        loadingDialog.dismiss();
//
//                    }
//                    else{
//                        Toast.makeText(SetsActivity.this, "No CAT Document Exists", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//
//                }else{
//                    Toast.makeText(SetsActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//                loadingDialog.cancel();
//            }
//        });
//
//














    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            SetsActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}