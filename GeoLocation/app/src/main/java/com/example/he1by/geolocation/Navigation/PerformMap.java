package com.example.he1by.geolocation.Navigation;

import android.graphics.Color;
import android.util.Log;

import com.example.he1by.geolocation.DataBase.DataBaseWork;
import com.example.he1by.geolocation.DataBase.Point;
import com.example.he1by.geolocation.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.TimerTask;
/**
 * Created by He1by on 4/7/2017.
 */


public class PerformMap extends TimerTask {
    @Override
    public void run() {

        MapsActivity.mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Point> points = DataBaseWork.GetPoints(MapsActivity.mContext);

                Log.d("test", "perform: " + points.size());

                for (int i = 0; i < points.size(); i++) {

                    if (i == 0 || i == points.size()-1) {
                        LatLng pt = new LatLng(points.get(i).lat, points.get(i).lon);
                        MapsActivity.mMap.addMarker(new MarkerOptions().position(pt).title(points.get(i).time).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_point_way)));
                        MapsActivity.mMap.moveCamera(CameraUpdateFactory.newLatLng(pt));
                    }
                    else {
                        LatLng pt = new LatLng(points.get(i).lat, points.get(i).lon);
                        MapsActivity.mMap.addMarker(new MarkerOptions().position(pt).title(points.get(i).time).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_way)));
                        MapsActivity.mMap.moveCamera(CameraUpdateFactory.newLatLng(pt));
                    }
                }

                PolylineOptions way = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                for (int i = 0; i < points.size(); i++) {
                    LatLng point = new LatLng(points.get(i).lat, points.get(i).lon);
                    way.add(point);
                }
                MapsActivity.mMap.addPolyline(way);
            }
        });
    }
}
