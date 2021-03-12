package com.project.quizapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecView> {

    List<CategoryModelClass> list;
   // public final Context context;
    public RecyclerAdapter(List<CategoryModelClass> list){
        this.list = list;
//        this.context = context;
    }


    @NonNull
    @Override
    public RecView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout, parent, false);
        return new RecView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecView holder, int position) {

        holder.txt.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    public void updateData(List<CategoryModelClass> List){
//        catList = catList;
//        notifyDataSetChanged();
//    }

//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(parent.getContext(), SetsActivity.class);
//        parent.getContext().startActivity(intent);
//    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(thish, SetsActivity.class);
//        context.startActivity(intent);
//    }



    public class RecView extends RecyclerView.ViewHolder{
        TextView txt;
        public RecView(@NonNull View itemView){
            super(itemView);
//            context = itemView.getContext();
           txt =  ((TextView) itemView.findViewById(R.id.catName));


        }


    }




}














//
//
//
//public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
//
//    private String[] mData;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//
//    // data is passed into the constructor
//    MyRecyclerViewAdapter(Context context, String[] data) {
//        this.mInflater = LayoutInflater.from(context);
//        this.mData = data;
//    }
//
//    // inflates the cell layout from xml when needed
//    @Override
//    @NonNull
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    // binds the data to the TextView in each cell
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.myTextView.setText(mData[position]);
//    }
//
//    // total number of cells
//    @Override
//    public int getItemCount() {
//        return mData.length;
//    }
//
//
//    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView myTextView;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            myTextView = itemView.findViewById(R.id.info_text);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
//    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData[id];
//    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position);
//    }
//}
