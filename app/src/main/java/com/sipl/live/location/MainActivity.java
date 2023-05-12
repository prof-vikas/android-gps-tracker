package com.sipl.live.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements LocationListener {

    private static final String TAG = "MainActivityErrors";
    private LocationManager locationManager;

    private String provider;

    double lng;
    double lat;

    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (provider == null || !locationManager.isProviderEnabled(provider)) {
            provider = LocationManager.GPS_PROVIDER;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
        } else {
            startLocationUpdates();
        }


    }

    private void startLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        } catch (SecurityException e) {
            Log.e(TAG, "Location permissions not granted.");
        }
    }

    @Override
    public void onLocationChanged(Location location) {
         lat = location.getLatitude();
         lng = location.getLongitude();
         float laat = (float) lat;
         float lonng = (float) lng;
        locationBasedOnName(laat,lonng);
        Log.d(TAG, "Latitude: " + lat + " Longitude: " + lng);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Do nothing
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Do nothing
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                // Permission denied, handle the error
            }
        }
    }

    private void locationBasedOnName(float lat, float lng){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String locationName = "New York City"; // The location name you want to convert to latitude and longitude
        List<Address> addressList = null;

        try {
//            addressList = geocoder.getFromLocationName(locationName, 1);
            addressList = geocoder.getFromLocation(lat,lng,1);
            Log.i(TAG, "locationBasedOnName: " + addressList.size());
            for (Address s: addressList) {
                Log.i(TAG, "locationBasedOnName: getAdminArea : " + s.getAdminArea());
                Log.i(TAG, "locationBasedOnName: getCountryCode : " + s.getCountryCode());
                Log.i(TAG, "locationBasedOnName: getFeatureName : " + s.getFeatureName());
                Log.i(TAG, "locationBasedOnName: getPhone : " + s.getPhone());
                Log.i(TAG, "locationBasedOnName: getPostalCode : " + s.getPostalCode());
                Log.i(TAG, "locationBasedOnName: getSubAdminArea : " + s.getSubAdminArea());
                Log.i(TAG, "locationBasedOnName: getSubLocality : " + s.getSubLocality());
                Log.i(TAG, "locationBasedOnName: getUrl : " + s.getUrl());
                Log.i(TAG, "locationBasedOnName: getExtras : " + s.getExtras());
                Log.i(TAG, "locationBasedOnName: getLongitude : " + s.getLongitude());
                Log.i(TAG, "locationBasedOnName: getLatitude : " + s.getLatitude());
                Log.i(TAG, "locationBasedOnName: getSubThoroughfare : " + s.getSubThoroughfare());
                Log.i(TAG, "locationBasedOnName: getMaxAddressLineIndex : " + s.getMaxAddressLineIndex());
                Log.i(TAG, "locationBasedOnName: describeContents : " + s.describeContents());
                Log.i(TAG, "locationBasedOnName: getCountryName : " + s.getCountryName());
                Log.i(TAG, "locationBasedOnName: getLocality : " + s.getLocality());
                Log.i(TAG, "locationBasedOnName: getPremises : " + s.getPremises());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressList != null && addressList.size() > 0) {
            Address address = addressList.get(0);
            double latitude = address.getLatitude();
            double longitude = address.getLongitude();
            // Use the latitude and longitude to display the location on the map or perform other actions
        }else {
            Log.i(TAG, "locationBasedOnName: addressList is null");
        }
    }

}