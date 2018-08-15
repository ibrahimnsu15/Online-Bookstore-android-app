package com.onlinebookstore.onlinebookstore;

public class Book {

    private String bookName;
    private String authorName;
    private String isbnCode;

    public Book(){

    }

    public Book(String name,String author,String isbn){
        bookName=name;
        authorName=author;
        isbnCode=isbn;
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
}
