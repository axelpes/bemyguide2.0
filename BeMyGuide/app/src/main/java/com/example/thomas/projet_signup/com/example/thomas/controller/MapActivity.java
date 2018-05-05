package com.example.thomas.projet_signup.com.example.thomas.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.thomas.projet_signup.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Attributes
    private GoogleMap mMap;
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // MAP
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // LIST
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MapActivity.this, PlaceActivity.class);
                //based on item add info to intent
                startActivity(intent);}
        });



        showListItems();
    }

    private List<PlaceItem> genererItems(){
        List<PlaceItem> items = new ArrayList<PlaceItem>();
        items.add(new PlaceItem(R.drawable.restaurants_checked, "Restaurant 1", "34, Rue Silvabelle", "1,7 km"));
        items.add(new PlaceItem(R.drawable.restaurants_checked, "Restaurant 2", "21, Impasse de Camille", "3,4 km"));
        items.add(new PlaceItem(R.drawable.museums_checked, "Musée 1", "90, Boulevard Pierre Dupont", "5,1 km"));
        items.add(new PlaceItem(R.drawable.pubs_checked, "Bar 1", "21, Rue du Vieux Port", "6,2 km"));
        items.add(new PlaceItem(R.drawable.restaurants_checked, "Restaurant 5", "171, Avenue de Luminy", "9,0 km"));
        return items;
    }

    private void showListItems(){
        List<PlaceItem> items = genererItems();

        PlaceAdapter adapter = new PlaceAdapter(MapActivity.this, items);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}
