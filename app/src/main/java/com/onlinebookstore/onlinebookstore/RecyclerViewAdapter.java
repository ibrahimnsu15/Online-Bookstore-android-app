package com.onlinebookstore.onlinebookstore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.bookName.setText(mData.get(position).getBookName());
        holder.authurName.setText("Author: "+mData.get(position).getAuthorName());
        holder.isbnCode.setText("ISBN: "+mData.get(position).getIsbnCode());
        holder.rating.setText("Rating: "+mData.get(position).getRating());
        loadImagefromURL(holder,mData.get(position).getImageUrl());

        //card click listener
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,BookActivity.class);

                // data pass
                intent.putExtra("imageURL",mData.get(position).getImageUrl());
                intent.putExtra("bookTitle",mData.get(position).getBookName());
                intent.putExtra("Author",mData.get(position).getAuthorName());
                intent.putExtra("ISBN",mData.get(position).getIsbnCode());
                intent.putExtra("description",mData.get(position).getDetails());


                intent.putExtra("rating",mData.get(position).getRating());
                intent.putExtra("ratingCount",mData.get(position).getRatingCount());


                mContext.startActivity(intent);
            }
        });

    }

    public void loadImagefromURL(@NonNull MyViewHolder holder,String url){
        Picasso.get().load(url).placeholder(R.drawable.bookcover)
                .error(R.drawable.bookcover)
                .into(holder.bookCover);
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView bookName;
        TextView authurName;
        TextView isbnCode;
        TextView rating;
        ImageView bookCover;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.cardBookName);
            authurName = itemView.findViewById(R.id.cardAuthorName);
            isbnCode = itemView.findViewById(R.id.cardISBN);
            rating = itemView.findViewById(R.id.cardRating);
            bookCover=itemView.findViewById(R.id.cardImage);
            card = itemView.findViewById(R.id.cardViewId);
        }
    }

}
