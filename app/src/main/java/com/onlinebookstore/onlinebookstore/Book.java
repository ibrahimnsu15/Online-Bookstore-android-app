package com.onlinebookstore.onlinebookstore;

import org.json.JSONObject;

public class Book {

    private String bookName;
    private String authorName;
    private String isbnCode;
    private String details;
    private String imageUrl;
    private double rating;
    private int ratingCount;


    public Book(){

    }

    public Book(String name,String author,String isbn,String detail,String imageurl,double bookrating,int count){
        bookName=name;
        authorName=author;
        isbnCode=isbn;
        details = detail;
        imageUrl = imageurl;
        rating = bookrating;
        ratingCount=count;
    }


    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(String isbnCode) {
        this.isbnCode = isbnCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
