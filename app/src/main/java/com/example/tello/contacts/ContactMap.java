package com.example.tello.contacts;

import android.Manifest;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tello.contacts.route.*;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class ContactMap extends Activity {

    double lat , lon;
    String name;
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_map);
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getDouble("lat");
            lon = extras.getDouble("lon");
            name = extras.getString("name");
        }

        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap != null) {
            LatLng position = new LatLng(lat, lon);
            Marker contactLocation = googleMap.addMarker(new MarkerOptions().position(position).title(name));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 7));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.getUiSettings().setCompassEnabled(true);
        }

        Button itin = (Button) findViewById(R.id.itin);
        itin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GPSTracker gps = new GPSTracker(getApplicationContext());
                double myLat = gps.getLatitude();
                double myLon = gps.getLongitude();
                LatLng start = new LatLng(myLat,myLon);
                LatLng end = new LatLng(lat,lon);
                Routing routing = new Routing.Builder()
                        .travelMode(Routing.TravelMode.DRIVING)
                        .withListener(new RoutingListener() {
                            @Override
                            public void onRoutingFailure() {
                            }

                            @Override
                            public void onRoutingStart() {
                            }

                            @Override
                            public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
                                PolylineOptions polyoptions = new PolylineOptions();
                                polyoptions.color(Color.BLUE);
                                polyoptions.width(10);
                                polyoptions.addAll(route.get(shortestRouteIndex).getPoints());
                                googleMap.addPolyline(polyoptions);
                            }

                            @Override
                            public void onRoutingCancelled() {
                            }
                        })
                        .waypoints(start, end)
                        .build();
                routing.execute();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
