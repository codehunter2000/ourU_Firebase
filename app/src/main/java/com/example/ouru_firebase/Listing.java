package com.example.ouru_firebase;

public class Listing
{
    private String title;
    private String author;
    private String isbn;
    private String condition;
    private String description;

    public Listing()
    {
        title = null;
        author = null;
        isbn = null;
        condition = null;
        description = null;
    }

    public Listing(String title, String author, String isbn, String condition, String description)
    {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.condition = condition;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
