package com.example.ouru_firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.List;

public class ListingsInfoAdapter extends ArrayAdapter<Listing>
{
    private Activity context;
    private List<Listing> listings;

    public ListingsInfoAdapter(Activity context, List<Listing> listings)
    {
        super(context, R.layout.activity_listings_page, listings);
        this.context = context;
        this.listings = listings;
    }

   @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       LayoutInflater layoutInflater = context.getLayoutInflater();
       View listView = layoutInflater.inflate(
               R.layout.activity_listings_page, null, true);
       Listing temp = listings.get(position);

       /*
            TODO:
                Use the temp Listing variable to access necessary data fields (ISBN, Title,
                    Author, etc.) and display them on the Listings layout page.
                Set all the necessary text fields, then return the listView item.

        */

       return listView;
    }
}
