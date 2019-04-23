package com.example.ouru_firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListingsPage extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings_page);

        listView = (ListView)findViewById(R.id.listview);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Book 1");
        arrayList.add("Book 2");
        arrayList.add("Book 3");
        arrayList.add("Book 4");
        arrayList.add("Book 5");
        arrayList.add("Book 6");
        arrayList.add("Book 7");
        arrayList.add("Book 8");
        arrayList.add("Book 9");
        arrayList.add("Book 10");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }
}
