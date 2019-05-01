package com.example.ouru_firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListingsPage extends AppCompatActivity {

    private ArrayList<Listing> listings;
    private ListView listView;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings_page);

        listView = (ListView)findViewById(R.id.listview);
        databaseReference = FirebaseDatabase.getInstance().getReference("listings");
        listings = new ArrayList<>();

//        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listings);
//        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot temp : dataSnapshot.getChildren())
                {
                    Listing listing = temp.getValue(Listing.class);
                    listings.add(listing);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(
                        ListingsPage.this,android.R.layout.simple_list_item_1, listings);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
