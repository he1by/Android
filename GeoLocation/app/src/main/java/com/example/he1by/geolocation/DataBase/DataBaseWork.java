package com.example.he1by.geolocation.DataBase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
/**
 * Created by He1by on 4/7/2017.
 */




public class DataBaseWork {

    public static DataBaseHelper dbHelper;

    public static void EnterPoint(Context context, Point pt) {

        dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues cv = new ContentValues();

        cv.put("lon", String.valueOf(pt.lon));
        cv.put("lat", String.valueOf(pt.lat));
        cv.put("time", pt.time);

        db.insert("points", null, cv);

        Log.d("test", pt.lat + " " + pt.lon + " " + pt.time);
    }

    public static ArrayList<Point> GetPoints(Context context) {
        dbHelper  = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("points", null, null, null, null, null, null);

        ArrayList<Point> rez = new ArrayList<Point>();

        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int lon = c.getColumnIndex("lon");
            int lat = c.getColumnIndex("lat");
            int time = c.getColumnIndex("time");

            do {
                Point buf = new Point();
                buf.lon = Double.parseDouble(c.getString(lon));
                buf.lat = Double.parseDouble(c.getString(lat));
                buf.time = c.getString(time);
                rez.add(buf);
            } while (c.moveToNext());
        }

        c.close();
        return rez;
    }

    public static void DeletePoints(Context context) {
        dbHelper  = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete("points", null, null);
    }

}
