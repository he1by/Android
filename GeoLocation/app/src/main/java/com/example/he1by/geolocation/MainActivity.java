package com.example.he1by.geolocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 22) {
            int hasNavPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasNavPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    public void WifiNav(View v) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        mapIntent.putExtra("stat", "wifi");
        startActivity(mapIntent);
    }

    public void GpsNav(View v) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        mapIntent.putExtra("stat", "gps");
        startActivity(mapIntent);
    }
}

