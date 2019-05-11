package com.example.ouru_firebase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class AddListing extends AppCompatActivity {

    ImageView iv;
    Button postButton, cameraButton;
    EditText titleBox, authorBox, isbnBox, descriptionBox, priceBox;
    Spinner conditionSpinner;
    String title, author, isbn, condition, description, price;
    private StorageReference storageRef, imagesRef, listingRef;
    private static final String FILE_NAME = "/data/user/0/com.example.ouru_firebase/files/user.dat";
    private static final String TAG = "AddListing Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        final User user = getUserInfo();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        imagesRef = storageRef.child("images");
        iv = findViewById(R.id.book_picture);
        postButton = findViewById(R.id.post_button);
        cameraButton = findViewById(R.id.camera_button);
        titleBox = findViewById(R.id.enter_title);
        authorBox = findViewById(R.id.enter_author);
        isbnBox  = findViewById(R.id.enter_ISBN);
        descriptionBox = findViewById(R.id.enter_description);
        conditionSpinner = findViewById(R.id.choose_condition);
        priceBox = findViewById(R.id.enter_price);

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
                price = priceBox.getText().toString();
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                Listing toAdd = new Listing(title, author, isbn, condition, description,
                    user.getEmail(), price);
                database.child("listings").child(Integer.toString(toAdd.hashCode()))
                        .setValue(toAdd);
                listingRef = storageRef.child("images/" + toAdd.hashCode() + ".jpg");
                Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] imageData = bao.toByteArray();
                UploadTask uploadTask = listingRef.putBytes(imageData);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Post was not successful",
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"Post successful",
                                Toast.LENGTH_LONG).show();
                    }
                });
                boolean status = uploadTask.isComplete();
                titleBox.getText().clear();
                authorBox.getText().clear();
                isbnBox.getText().clear();
                conditionSpinner.setSelection(0);
                descriptionBox.getText().clear();
                priceBox.getText().clear();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bm = (Bitmap)data.getExtras().get("data");
        iv.setImageBitmap(bm);
    }

    private User getUserInfo()
    {
        try
        {
            FileInputStream inputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            User temp = (User) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
            return temp;
        }

        catch (Exception e)
        {
            Log.v(TAG, "Error saving user data");
        }

        return null;
    }

    public void viewListingsClicked(View view) {
        Intent goToListings = new Intent(this, ListingsPage.class);
        startActivity(goToListings);
    }

    public void myListingsClicked(View view) {
        //Intent goToMyListings= new Intent(this, MyListings.class);
        //startActivity(goToMyListings);
    }

    public void signOutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent goToSignInPage = new Intent(this,MainActivity.class);
        startActivity(goToSignInPage);
    }
}
