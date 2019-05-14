package com.example.ouru_firebase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MyListings extends AppCompatActivity {

    private static final String FILE_NAME = "/data/user/0/com.example.ouru_firebase/files/user.dat";
    private static final String TAG = "MyListings";
    private User user = null;
    private ArrayList<Listing> listings;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private DatabaseReference databaseReference, listingReferene;
    private StorageReference storageReference, pictureReference;
    private Listing tempListing = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listings);

        user = getUserInfo();
        listView = (ListView) findViewById(R.id.listview);
        databaseReference = FirebaseDatabase.getInstance().getReference("listings");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        listings = new ArrayList<>();


        final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Edit Listing")
                .setMessage("Select An Option")
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listingReferene = databaseReference.child(Integer.toString(tempListing.hashCode()));
                        listingReferene.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Log.v(TAG, "Listing remove successful");
                            }
                        });
                        pictureReference = storageReference.child("images/"
                                + tempListing.hashCode() + ".jpg");
                        pictureReference.delete();
                        dialog.dismiss();
                    }
                });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tempListing = (Listing) listView.getItemAtPosition(position);
                builder.show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot temp : dataSnapshot.getChildren()) {
                    Listing listing = temp.getValue(Listing.class);
                    if (listing != null)
                        if (listing.getEmail().equals(user.getEmail()))
                            listings.add(listing);
                }
                arrayAdapter = new ArrayAdapter(
                        MyListings.this, android.R.layout.simple_list_item_1, listings);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private User getUserInfo() {
        try {
            FileInputStream inputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            User temp = (User) objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
            return temp;
        } catch (Exception e) {
            Log.v(TAG, "Error reading user data");
        }

        return null;
    }

    public void createPostClicked(View view) {
        Intent goToAddListings = new Intent(this, AddListing.class);
        startActivity(goToAddListings);
    }

    public void viewListingsClicked(View view) {
        Intent goToListings = new Intent(this, ListingsPage.class);
        startActivity(goToListings);
    }

    public void signOutClicked(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent goToSignInPage = new Intent(this,MainActivity.class);
        startActivity(goToSignInPage);
    }
}
