package com.example.ouru_firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListingsPage extends AppCompatActivity {

    private ArrayList<Listing> listings;
    private ListView listView;
    private EditText editText;
    private ArrayAdapter arrayAdapter;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings_page);

        listView = (ListView)findViewById(R.id.listview);
        databaseReference = FirebaseDatabase.getInstance().getReference("listings");
        listings = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Listing listItem = (Listing)listView.getItemAtPosition(position);
                Intent goToIndividualListing = new Intent(view.getContext(), IndividualListing.class);
                goToIndividualListing.putExtra("Title", listItem.getTitle());
                goToIndividualListing.putExtra("ISBN", listItem.getIsbn());
                goToIndividualListing.putExtra("Condition", listItem.getCondition());
                goToIndividualListing.putExtra("Price", listItem.getPrice());
                goToIndividualListing.putExtra("Description", listItem.getDescription());
                goToIndividualListing.putExtra("Email", listItem.getEmail());
                goToIndividualListing.putExtra("Hash", listItem.hashCode());
                startActivity(goToIndividualListing);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (listings != null)
            listings.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot temp : dataSnapshot.getChildren())
                {
                    Listing listing = temp.getValue(Listing.class);
                    listings.add(listing);
                }
                arrayAdapter = new ArrayAdapter(
                        ListingsPage.this,android.R.layout.simple_list_item_1, listings);
                listView.setAdapter(arrayAdapter);

                editText = (EditText) findViewById(R.id.search_filter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        (ListingsPage.this).arrayAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void createPostClicked(View view) {
        Intent goToAddListings = new Intent(this, AddListing.class);
        startActivity(goToAddListings);
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
}
