package com.example.ouru_firebase;

import android.support.annotation.NonNull;

public class Listing
{
    private String title;
    private String author;
    private String isbn;
    private String condition;
    private String description;
    private String email;

    public Listing()
    {
        title = null;
        author = null;
        isbn = null;
        condition = null;
        description = null;
        email = null;
    }

    public Listing(String title, String author, String isbn, String condition,
                   String description, String email)
    {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.condition = condition;
        this.description = description;
        this.email = email;
    }

    @NonNull
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Title: " + title + "\n");
        stringBuilder.append("Author: " + author + "\n");
        stringBuilder.append("ISBN#: " + isbn + "\n");
        stringBuilder.append("Condition: " + condition + "\n");
        stringBuilder.append("Description: " + description + "\n");
        stringBuilder.append("Email: " + email);
        return stringBuilder.toString();
    }

    @Override
    public int hashCode()
    {
        int ascii = 0;
        for (int i = 0; i < title.length(); i++)
        {
            ascii += (int) title.charAt(i);
        }

        for (int i = 0; i < author.length(); i++)
        {
            ascii += (int) author.charAt(i);
        }

        for (int i = 0; i < isbn.length(); i++)
        {
            ascii += isbn.charAt(i);
        }

        for (int i = 0; i < condition.length(); i++)
        {
            ascii += condition.charAt(i);
        }

        for (int i = 0; i < description.length(); i++)
        {
            ascii += description.charAt(i);
        }

        for (int i = 0; i < email.length(); i++)
        {
            ascii += email.charAt(i);
        }
        return ascii;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
