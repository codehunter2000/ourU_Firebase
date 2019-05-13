package com.example.ouru_firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MyListings extends AppCompatActivity
{

    private static final String FILE_NAME = "/data/user/0/com.example.ouru_firebase/files/user.dat";
    private static final String TAG = "AddListing Activity";
    private User user = null;
    private ArrayList<Listing> listings;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listings);

        user = getUserInfo();
        listView = (ListView)findViewById(R.id.listview);
        databaseReference = FirebaseDatabase.getInstance().getReference("listings");
        listings = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // show listing options
            }
        });

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot temp : dataSnapshot.getChildren())
                {
                    Listing listing = temp.getValue(Listing.class);
                    if (listing != null)
                        if (listing.getEmail().equals(user.getEmail()))
                            listings.add(listing);
                }
                arrayAdapter = new ArrayAdapter(
                        MyListings.this,android.R.layout.simple_list_item_1, listings);
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
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
            Log.v(TAG, "Error reading user data");
        }

        return null;
    }
}
