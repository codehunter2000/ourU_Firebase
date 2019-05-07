package com.example.ouru_firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class IndividualListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_listing);
    }

    public void contactSellerClicked(View view) {
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"blah@blah.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Your book for sale on OurU");
        email.putExtra(Intent.EXTRA_TEXT, "I am interested in buying your book");
        try {
            startActivity(Intent.createChooser(email, "Send mail..."));
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(IndividualListing.this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
        }
    }
}
