package com.example.ouru_firebase;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class IndividualListing extends AppCompatActivity {

    private TextView title, isbn, condition, price, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_listing);

        title = (TextView)findViewById(R.id.title);
        isbn = (TextView)findViewById(R.id.ISBN);
        condition = (TextView)findViewById(R.id.condition);
        price = (TextView)findViewById(R.id.price);
        description = (TextView)findViewById(R.id.description);
    }

    public void contactSellerClicked(View view) {
        String[] TO = {""};
        String[] CC = {""};

        Intent email = new Intent(Intent.ACTION_SEND);
        email.setData(Uri.parse("mailto:"));
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_EMAIL,TO);
        email.putExtra(Intent.EXTRA_CC, CC);
        email.putExtra(Intent.EXTRA_SUBJECT, "Your book on OurU");
        email.putExtra(Intent.EXTRA_TEXT, "I am interested in buying your book");

        try {
            startActivity(Intent.createChooser(email, "Send mail..."));
            finish();
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(IndividualListing.this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
        }
    }
}
