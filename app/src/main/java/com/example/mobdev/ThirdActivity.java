package com.example.mobdev;
//Name - Richard Sharpe Marshall
//Matric Number - S1829121
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mobdev.Classes.PlannedParse;
import com.example.mobdev.Classes.set_items;
import com.example.mobdev.Recycler.RecycleAdaptor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.quadtree.PointQuadTree;

import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.Calendar;


public class ThirdActivity extends AppCompatActivity {
    private PlannedParse xmlObject;
    private RecycleAdaptor mAdapter;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private Boolean LocationsPermission = false;
    private static final int REQUEST_CODE = 1234;
    private GoogleMap gmap;
    private FusedLocationProviderClient LocProvClient;
    private static final float Default_Zoom = 15;
    private ArrayList<set_items> coordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.map_activity);


        xmlObject = new PlannedParse();

        try {
            mAdapter = new RecycleAdaptor(xmlObject.fetchXml());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        if(isServicesOK()){
            getLocationPermission();
        }
    }





    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                LocationsPermission = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this, permissions,REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this, permissions,REQUEST_CODE);
        }
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;

                getDeviceLocation();

                gmap.getUiSettings().setMyLocationButtonEnabled(false);

                if(LocationsPermission){
                    try{
                        coordList = (xmlObject.fetchXml());
                    }catch(XmlPullParserException e){
                    }



                    for(int i=0; i < coordList.size(); i++) {
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(coordList.get(i).getCoordinates())
                                .title(coordList.get(i).getTitle())
                                .snippet(coordList.get(i).getDescription());

                        gmap.addMarker(markerOptions);
                    }

                }
            }
        });
    }

    private void getDeviceLocation(){

        LocProvClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(LocationsPermission){
                Task location = LocProvClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener(){

                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Camera(new LatLng(55.86, -4.25), Default_Zoom);

                        }else{

                        }
                    }
                });
            }
        }catch(SecurityException e){

        }
    }

    public void Camera(LatLng latlng, float zoom){
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));

        MarkerOptions options = new MarkerOptions().position(latlng);
        gmap.addMarker(options);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LocationsPermission = false;
        switch(requestCode){
            case REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            LocationsPermission = false;
                            return;
                        }
                    }
                    LocationsPermission = true;
                    initMap();


                }
            }
        }
    }


    public boolean isServicesOK(){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ThirdActivity.this);
        if(available == ConnectionResult.SUCCESS){
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ThirdActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "Cannot connect to map services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

} // End of MainActivity