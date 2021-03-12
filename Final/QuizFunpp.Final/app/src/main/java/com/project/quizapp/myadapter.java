package com.project.quizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder> {

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model) {

        holder.name.setText(model.getName());
        holder.review.setText(model.getReview());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView name, review;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.reviewName);
            review = (TextView)itemView.findViewById(R.id.review);

        }
    }


}
