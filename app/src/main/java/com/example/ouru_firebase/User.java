package com.example.ouru_firebase;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName, lastName, email;

    public User() {
        firstName = null;
        lastName = null;
        email = null;
    }

    public User(String firstName, String lastName, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public int hashCode() {
        int ascii = 0;
        for (int i = 0; i < firstName.length(); i++)
        {
            ascii += firstName.charAt(i);
        }

        for (int i = 0; i < lastName.length(); i++)
        {
            ascii += lastName.charAt(i);
        }

        for (int i = 0; i < email.length(); i++)
        {
            ascii += email.charAt(i);
        }
        return ascii;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
