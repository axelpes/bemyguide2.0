package com.example.thomas.projet_signup.com.example.thomas.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.thomas.projet_signup.PlaceCategoryAdapter;
import com.example.thomas.projet_signup.R;
import com.example.thomas.projet_signup.com.example.thomas.model.CategoryInterests;
import com.example.thomas.projet_signup.com.example.thomas.model.PointOfInterest;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class HomeActivity extends AppCompatActivity {

    private ListView mListView;
    private TextView mTextView;
    private SeekBar mSeekBar;
    private Button search_button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mListView = (ListView)findViewById(R.id.placecategories_list);
        showListItems();

        search_button = (Button)findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        mTextView = (TextView)findViewById(R.id.radius_text);
        mTextView.setText("Search Radius : < 25km");
        mSeekBar = (SeekBar)findViewById(R.id.radius_seekbar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextView.setText("Search Radius : < "+progress+"km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private List<PointOfInterest> generateItems(){
        List<PointOfInterest> items = new ArrayList<>();
        TreeMap<String, ArrayList<PointOfInterest>> list = CategoryInterests.GetListPoints();
        Log.d("test", list.toString());
        for(PointOfInterest point : list.get("Miam Miam"))
                items.add(point);
        /*items.add(new PointOfInterest("Cinemas", "cinema", "checkbox_cinema","cinema_color"));
        items.add(new PointOfInterest("Hotels", "lodging" ,"checkbox_hotel", "hotel_color"));
        items.add(new PointOfInterest("Museums", "museum", "checkbox_museum", "museum_color"));
        items.add(new PointOfInterest("Pubs", "pub", "checkbox_pub","pub_color"));
        items.add(new PointOfInterest("Restaurants", "restaurant", "checkbox_restaurant", "restaurant_color"));
        items.add(new PointOfInterest("Theaters", "thaeter", "checkbox_theater", "theater_color"));*/

        return items;
    }

    private void showListItems(){
        List<PointOfInterest> items = generateItems();
        PlaceCategoryAdapter adapter = new PlaceCategoryAdapter(HomeActivity.this, items);
        mListView.setAdapter(adapter);
    }
}