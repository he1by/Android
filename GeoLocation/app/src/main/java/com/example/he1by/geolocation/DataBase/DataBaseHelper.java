package com.example.he1by.geolocation.DataBase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by He1by on 4/7/2017.
 */


public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        // конструктор суперкласса
        super(context, "NavDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицу с полями
        db.execSQL("create table points ("
                + "lat string,"
                + "lon string,"
                + "time string"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
