package com.project.quizapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.project.quizapp.LogIn.selected_cat_index_app;
import static java.security.AccessController.getContext;

//Creating the adapter wih the default BaseAdapter
//Also implementing

public class CatGridAdapter extends BaseAdapter {


    //List of all the class variables
    private List<CategoryModelClass> catList;
    public Context m;

    //Initializing the list wih constructor
    public CatGridAdapter(Context context, List<CategoryModelClass> catList) {
        this.catList = catList;
        this.m = context;
    }

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }
    public void updateData(List<CategoryModelClass> catList){
        this.catList = catList;
        this.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if(convertView == null){
            //Adding all the views as we get the data from firebase
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout,parent,false);
        }
        else{
            view = convertView;
        }



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selected_cat_index_app = position;
                Intent intent = new Intent(parent.getContext(), SetsActivity.class);
                intent.putExtra("NAME", catList.get(position).getName());
                intent.putExtra("ID", catList.get(position).getId());

                parent.getContext().startActivity(intent);
                Log.d("SIZE", catList.get(position).getName());
//                m.startActivity(new Intent(m, SetsActivity.class));
            }
        });

        ((TextView) view.findViewById(R.id.catName)).setText(catList.get(position).getName());


        Random rnd = new Random();
        int color = Color.argb(255,rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255));
        view.setBackgroundColor(color);
        return view;
    }


}
