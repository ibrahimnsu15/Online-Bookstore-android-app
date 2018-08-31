package com.onlinebookstore.onlinebookstore;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BookActivity extends AppCompatActivity {

    private ImageView bookCover;
    private TextView bookTitle;
    private TextView authorName;
    private TextView isbnCode;
    private TextView ratingUserCount;
    private RatingBar ratingBar;
    private TextView Description;
    private Button GiveRatingButton;
    //private Button RatingSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //assign id
        bookCover = findViewById(R.id.clickImageView);
        bookTitle = findViewById(R.id.clickTitleView);
        authorName = findViewById(R.id.clickAuthorName);
        isbnCode = findViewById(R.id.clickISBN);
        ratingUserCount = findViewById(R.id.clickUserRatingCount);
        ratingBar = findViewById(R.id.clickRatingRingBar);
        Description = findViewById(R.id.clickBookDescription);
        GiveRatingButton = findViewById(R.id.clickGiveRating);
      //  RatingSubmitButton = findViewById(R.id.DialogSubmit);

        /// Receive data
        Intent intent = getIntent();

        String imageUrl = intent.getExtras().getString("imageURL");
        String title = intent.getExtras().getString("bookTitle");
        String author = intent.getExtras().getString("Author");
        String isbn = intent.getExtras().getString("ISBN");
        Integer ratingCount = intent.getExtras().getInt("ratingCount");
        Double rating = intent.getExtras().getDouble("rating");
        String description = intent.getExtras().getString("description");

        Log.d("url", "onCreateBookActivity: "+imageUrl);
        Log.d("title", "onCreateBookActivity: "+title);
        Log.d("author", "onCreateBookActivity: "+author);
        Log.d("isbn", "onCreateBookActivity: "+isbn);
        Log.d("ratingCount", "onCreateBookActivity: "+ratingCount);
        Log.d("rating", "onCreateBookActivity: "+rating);
        Log.d("description", "onCreateBookActivity: "+description);

        /// set data in layout
        bookTitle.setText(title);
        authorName.setText("Author: "+author);
        isbnCode.setText("ISBN: "+isbn);
        ratingUserCount.setText(ratingCount.toString()+" users");
        ratingBar.setRating(Float.parseFloat(rating.toString()));
        Description.setText(description);

        Picasso.get().load(imageUrl).placeholder(R.drawable.bookcover)
                .error(R.drawable.bookcover)
                .into(bookCover);

        GiveRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(BookActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_rating_bar,null);


                mBuilder.setView(mView);
                final AlertDialog dialog= mBuilder.create();
                dialog.show();
            }
        });




    }
}
