package com.project.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class myhistadapter extends FirebaseRecyclerAdapter<histmodel, myhistadapter.myhistviewholder> {

    public myhistadapter(@NonNull FirebaseRecyclerOptions<histmodel> options) {
        super(options);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userEmail = user.getEmail();
        } else {
        }


    }
    String userEmail;





    @Override
    protected void onBindViewHolder(@NonNull myhistviewholder holder, int position, @NonNull histmodel histmodel) {

        //Checking the email of the current user and filtering all the unique views
//        if(histmodel.getEmail().equals(userEmail.toString())){
//
//            holder.histemail.setVisibility(View.VISIBLE);
//            holder.histscore.setVisibility(View.VISIBLE);
//            holder.histcat.setVisibility(View.VISIBLE);
//            holder.histset.setVisibility(View.VISIBLE);
//
//
//
//
//
//        }
//        else{
//
//            holder.histscore.setTextColor(View.GONE);
//            holder.histcat.setVisibility(View.GONE);
//            holder.histset.setVisibility(View.GONE);
//            holder.histemail.setVisibility(View.GONE);
//
//
//
//
//
//  }
        holder.histemail.setText(histmodel.getEmail());
        holder.histscore.setText(histmodel.getScore());
        holder.histcat.setText(histmodel.getCat());
        holder.histset.setText(histmodel.getSet());
    }

    @NonNull
    @Override
    public myhistviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent,false);
        return new myhistviewholder(view);
    }

    class myhistviewholder extends RecyclerView.ViewHolder{
        TextView histemail, histcat, histset, histscore;
        //RecyclerView rec;
        public myhistviewholder(@NonNull View itemView) {
            super(itemView);

            //binding to the layout file
           // rec = (RecyclerView)itemView.findViewById(R.id.hisrecview);
            histemail = (TextView)itemView.findViewById(R.id.histemail);
            histcat = (TextView)itemView.findViewById(R.id.histcat);
            histset = (TextView)itemView.findViewById(R.id.histset);
            histscore = (TextView)itemView.findViewById(R.id.histscore);



        }
    }


}
