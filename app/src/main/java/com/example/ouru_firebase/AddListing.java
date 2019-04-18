package com.example.ouru_firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddListing extends AppCompatActivity {

    ImageView iv;
    Button postButton, cameraButton;
    EditText titleBox, authorBox, isbnBox, descriptionBox;
    Spinner conditionSpinner;
    String title, author, isbn, condition, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);

        iv = findViewById(R.id.book_picture);
        postButton = findViewById(R.id.post_button);
        cameraButton = findViewById(R.id.camera_button);
        titleBox = findViewById(R.id.enter_title);
        authorBox = findViewById(R.id.enter_author);
        isbnBox  = findViewById(R.id.enter_ISBN);
        descriptionBox = findViewById(R.id.enter_description);
        conditionSpinner = findViewById(R.id.choose_condition);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, 0);

            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                title = titleBox.getText().toString();
                author = authorBox.getText().toString();
                isbn = isbnBox.getText().toString();
                condition = conditionSpinner.getSelectedItem().toString();
                description = descriptionBox.getText().toString();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                Listing toAdd = new Listing(title, author, isbn, condition, description);
                database.child("listings").child(Integer.toString(toAdd.hashCode()))
                        .setValue(toAdd);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bm = (Bitmap)data.getExtras().get("data");
        iv.setImageBitmap(bm);
    }
}
