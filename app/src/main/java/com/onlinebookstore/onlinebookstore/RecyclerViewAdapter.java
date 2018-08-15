package com.onlinebookstore.onlinebookstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    private List<Book> mData;

    public RecyclerViewAdapter(Context mContext,List<Book> mData){
        this.mContext=mContext;
        this.mData=mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.cardview_item_book,parent,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bookName.setText(mData.get(position).getBookName());
        holder.authurName.setText("Author: "+mData.get(position).getAuthorName());
        holder.isbnCode.setText("ISBN: "+mData.get(position).getIsbnCode());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView bookName;
        TextView authurName;
        TextView isbnCode;

        public MyViewHolder(View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.cardBookName);
            authurName = itemView.findViewById(R.id.cardAuthorName);
            isbnCode = itemView.findViewById(R.id.cardISBN);



        }
    }

}
