package com.project.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.SearchView;

import static com.project.quizapp.LogIn.catList;

import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity {

//    CatGridAdapter adapter;
    CatGridAdapter adapter;
private GridView catGrid;
    private FirebaseFirestore firebasefirestore;
    public static List<CategoryModelClass> resultData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Binding view and adding toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Subjects");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   startActivity(new Intent(HomePage.this, dashActivity.class));

            }
        });
        //Adapter to set all the views
        catGrid = findViewById(R.id.catGridvie);
        //catGrid.setLayoutManager(new GridLayoutManager(this, 2));
        Context context;
        adapter = new CatGridAdapter(HomePage.this, catList);
        catGrid.setAdapter(adapter);



    }



    //Filtering all tthe views
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflating all the views and filtering
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuitem = menu.findItem(R.id.searchMenu);
        SearchView searchview = (SearchView) menuitem.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Calling filters on the search attempt
                //adapter.getFilter().filter(newText);
                resultData = new ArrayList<>();
                for(CategoryModelClass categoryModelClass:catList){
                    if(categoryModelClass.getName().toLowerCase().contains(newText.toLowerCase())){
                        resultData.add(categoryModelClass);
                    }

//                    adapter = new RecyclerAdapter(resultData);
//
//                    catGrid.setAdapter(adapter);

                    adapter.updateData(resultData);

                }


                return true;
  }
        });
        return true;

    }

    //Adding search icon to the toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.searchMenu){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




//    //loading data from the firestore //getting subjects as categories
//    private void loadData(){
//        catList.clear();
//        firebasefirestore.collection("QUIZ").document("Category")
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//
//                    //on successful handeling, assigning values
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
//                        //on failure excepion message display
//                        Toast.makeText(HomePage.this, "No Category Document Exists", Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//
//                }else{
//                    Toast.makeText(HomePage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//
//    }

}