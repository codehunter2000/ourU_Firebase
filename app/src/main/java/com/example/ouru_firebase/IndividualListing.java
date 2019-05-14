package com.example.ouru_firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class IndividualListing extends AppCompatActivity {

    private TextView title, isbn, condition, price, description;
    private ImageView picture;
    private String hashCode;
    private StorageReference storageReference, imageReference;
    private static final String TAG = "IndividualListing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_listing);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        title = (TextView)findViewById(R.id.title);
        isbn = (TextView)findViewById(R.id.ISBN);
        condition = (TextView)findViewById(R.id.condition);
        price = (TextView)findViewById(R.id.price);
        description = (TextView)findViewById(R.id.description);
        picture = findViewById(R.id.book_image);

        Intent intent = getIntent();
        title.setText("Title: " + intent.getExtras().getString("Title"));
        isbn.setText("ISBN: " + intent.getExtras().getString("ISBN"));
        condition.setText("Condition : " + intent.getExtras().getString("Condition"));
        price.setText("Price: " + intent.getExtras().getString("Price"));
        description.setText("Description: " + intent.getExtras().getString("Description"));
        hashCode = Integer.toString(intent.getExtras().getInt("Hash"));

        imageReference = storageReference.child("images/" + hashCode + ".jpg");
        final long ONE_MEGABYTE = 1024 * 1024;
        imageReference.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>()
        {
            @Override
            public void onSuccess(byte[] bytes)
            {
                Log.v(TAG, "Image download success");
                Bitmap bits = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                picture.setImageBitmap(bits);
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.v(TAG, "Image download failed");
            }
        });
    }

    public void contactSellerClicked(View view) {
        String[] CC = {""};
        String[] SUBJECT = {""};
        String[] BODY = {""};
        Intent intent = getIntent();
        String[] EMAIL = {intent.getExtras().getString("Email")};

        Intent email = new Intent(Intent.ACTION_SEND);
        email.setData(Uri.parse("mailto:"));
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_EMAIL,EMAIL);
        email.putExtra(Intent.EXTRA_CC, CC);
        email.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        email.putExtra(Intent.EXTRA_TEXT, BODY);

        try {
            startActivity(Intent.createChooser(email, "Send mail..."));
            finish();
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(IndividualListing.this, "There are no email clients installed.", Toast.LENGTH_LONG).show();
        }
    }

    public void viewListingsClicked(View view) {
        Intent goToListings = new Intent(this, ListingsPage.class);
        startActivity(goToListings);
    }

    public void myListingsClicked(View view) {
        Intent goToMyListings= new Intent(this, MyListings.class);
        startActivity(goToMyListings);
    }

    public void signOutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent goToSignInPage = new Intent(this,MainActivity.class);
        startActivity(goToSignInPage);
    }

    public void createPostClicked(View view) {
        Intent goToAddListings = new Intent(this, AddListing.class);
        startActivity(goToAddListings);
    }
}
