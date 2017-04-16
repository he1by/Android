package com.example.he1by.geolocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.he1by.geolocation.Navigation.NavCor;
import com.example.he1by.geolocation.Navigation.PerformMap;

import java.util.Timer;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final long TIME_BW_UPDATES = 1000*10*1;
    private static final long METERS_BW_UPDATES = 2;

    public static GoogleMap mMap;
    private String NavStat;
    private NavCor Core;
    private Timer mTimer;
    private PerformMap mPerform;
    public static MapsActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        NavStat = getIntent().getStringExtra("stat");
        Core = new NavCore(NavStat, this);

        mContext = this;

        getOverflowMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            if (Core.EnableGPS)
                if (Core.locationManager != null) Core.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_BW_UPDATES, METERS_BW_UPDATES, Core.locationListener);
            if (Core.EnableWiFi)
                if (Core.locationManager != null) Core.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, TIME_BW_UPDATES, METERS_BW_UPDATES, Core.locationListener);
        }
        catch (SecurityException e) {
            checkEnabled();
        }
    }

    private void checkEnabled() {
        if (Core.locationManager != null) {
            Toast.makeText(this, "Wifi: " + Core.locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER) + " GPS: " + Core.locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (Core.locationManager != null) {
                Core.locationManager.removeUpdates(Core.locationListener);
            }
        }
        catch (SecurityException e) {
            checkEnabled();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_items_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch(item.getItemId()){

            case R.id.start:
                Core.Start();

                mTimer = new Timer();
                mPerform = new PerformMap();
                mTimer.schedule(mPerform, 0, TIME_BW_UPDATES);

                break;

            case R.id.stop:
                Core.Stop();
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                break;

            case R.id.reset:
                DataBaseWork.DeletePoints(this);
                break;

        }
        return true;

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}

