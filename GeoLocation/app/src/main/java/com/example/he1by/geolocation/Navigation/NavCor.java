package com.example.he1by.geolocation.Navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.location.LocationListener;
import android.widget.Toast;

import com.example.he1by.geolocation.DataBase.DataBaseWork;
import com.example.he1by.geolocation.DataBase.Point;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.LOCATION_SERVICE;
/**
 * Created by He1by on 4/7/2017.
 */




public class NavCor{
    public LocationManager locationManager;
    public Context context;
    StringBuilder sbGPS = new StringBuilder();
    StringBuilder sbNet = new StringBuilder();

    private static final long TIME_BW_UPDATES = 1000*60*1;
    private static final long METERS_BW_UPDATES = 10;

    public Boolean EnableGPS = false;
    public Boolean EnableWiFi = false;

    public NavCor(String NavStat, Context _context) {
        if (NavStat.equals("wifi")) EnableWiFi = true;
        if (NavStat.equals("gps")) EnableGPS = true;

        //locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        context = _context;
    }

    public void Start() {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        try {
            if (EnableGPS) locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_BW_UPDATES, METERS_BW_UPDATES, locationListener);
            if (EnableWiFi) locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME_BW_UPDATES, METERS_BW_UPDATES, locationListener);
        }
        catch (SecurityException e) {
            Toast.makeText(context, "Wifi: " + locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER) + " GPS: " + locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER), Toast.LENGTH_LONG).show();
        }
    }

    public void Stop() {
        try {
            if (locationManager != null)
                locationManager.removeUpdates(locationListener);
        }
        catch (SecurityException e) {
            Toast.makeText(context, "Wifi: " + locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER) + " GPS: " + locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER), Toast.LENGTH_LONG).show();
        }
    }

    public LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    private void showLocation(Location location) {
        if (location == null) return;

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Point newPoint = new Point();
        newPoint.lat = location.getLatitude();
        newPoint.lon = location.getLongitude();
        newPoint.time = df.format(location.getTime());

        DataBaseWork.EnterPoint(context, newPoint);
    }
}
