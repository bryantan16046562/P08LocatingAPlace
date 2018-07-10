package com.example.a16046562.p08_locatingaplace;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {
    Button btnorth, btncentrall, btneastt;
    private GoogleMap map;
    LatLng poi_north, poi_east, poi_central, poi_singapore;
    Marker northh, central, east;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback(){
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                //show user current location on the map
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                }
                if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    return ;
                }

                //setting specific controls on the map
                UiSettings ui = map.getUiSettings();
                ui.setAllGesturesEnabled(true);
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);
                ui.setMyLocationButtonEnabled(true);

                // show certain specific location upon first opening the app
                poi_singapore = new LatLng(1.3437298, 103.5431174);
                map.moveCamera(CameraUpdateFactory.newLatLng(poi_singapore));

                //north
                poi_north = new LatLng(1.4508381, 103.81576470000005);
                northh = map.addMarker(new
                        MarkerOptions()
                        .position(poi_north)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.starbg)));

                //central
                poi_central = new LatLng(1.2976615, 103.84748560000003);
                central = map.addMarker(new
                        MarkerOptions()
                        .position(poi_central)
                        .title("Central")
                        .snippet("Block 3A, Orchard Ave 3, 134542")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                //east
                poi_east = new LatLng(1.3500005, 103.9316365);
                east = map.addMarker(new
                        MarkerOptions()
                        .position(poi_east)
                        .title("East")
                        .snippet("Block 555, Tampines Ave 3, 287788")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


                // 2nd way of getting toast message on each of the markers upon being clicked
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String title = marker.getTitle();
                        Toast.makeText(MainActivity.this, title, Toast.LENGTH_LONG).show();

                        return false;
                    }
                });
            }
        });

        //spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int selectedItem = spinner.getSelectedItemPosition();
                if (selectedItem == 0){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north,15));
                } else if(selectedItem == 1){
                    if (map != null){
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,15));
                    }
                } else if(selectedItem == 2){
                    if (map != null){
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east,15));
                    }
                } else {
                    if (map != null){
                        map.moveCamera(CameraUpdateFactory.newLatLng(poi_singapore));
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnorth = (Button) findViewById(R.id.btnNorth);
        btnorth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north,15));

                    //1st way of customising individual action taken
                    //Toast.makeText(MainActivity.this, northh.getTitle().toString() , Toast.LENGTH_SHORT).show();
                }
            }
        });
        btncentrall = (Button) findViewById(R.id.btnCentral);
        btncentrall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,15));

                    //1st way of customising individual action taken
                    //Toast.makeText(MainActivity.this, central.getTitle().toString() , Toast.LENGTH_SHORT).show();
                }
            }
        });
        btneastt = (Button) findViewById(R.id.btnEast);
        btneastt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map != null){
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east,15));

                    //1st way of customising individual action taken
                    //Toast.makeText(MainActivity.this, east.getTitle().toString() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
