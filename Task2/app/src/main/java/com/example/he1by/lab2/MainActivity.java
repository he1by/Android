package com.example.he1by.lab2;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_VIDEO_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button recordButton = (Button) findViewById(R.id.RecordButton);
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            recordButton.setEnabled(false);
        }
    }

    public void openCamera(View view) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        File file = new File(path, "TAV/" + dateFormat.format(date) + ".mp4");
        Uri videoUri = Uri.fromFile(file);
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);

        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Video saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Failed to record video",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}



