package com.joshuakristanto.BusStop;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.hardware.Sensor.TYPE_GYROSCOPE;


public class Analysis extends WearableActivity {
    private TextView mTextView;
    SensorManager mSensorManager;
    SensorEventListener listener2;
    LocationManager manager;
    LocationListener listener;
    Geocoder gcd;
    List<Address> address;
    Location last;
    double distance = 0;
    double distanceRange = 3;
    double latitude = 0;
    double longitude = 0;
    String locationList[];
    SimpleDateFormat simpleDateFormat;
    String averageValues = "";
    boolean firstRun = true;
    boolean reset = true;
    boolean trigger = false;
    boolean trigger2 = false;
    int iteration = 0;
    int printIteration;
    String output;
    public Button button;
    public Button button2;

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
//            trigger = true;
        };
        setContentView(R.layout.analysis);


        button = (Button) findViewById(R.id.button5);
        button2 = (Button) findViewById(R.id.button6);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("hh_mm_ss");
                // Perform action on click
//               writeToFile(output);
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/LocationData", "text");
                if (!file.exists()) {
                    Log.i("HELLO WORLD", ""+file);
                    file.mkdirs();
                }
                try {
                    File gpxfile = new File(file, simpleDateFormat2.format(new Date())+"sample.csv");
                    Log.i("HELLO WORLD2 " ,""+gpxfile);
                    FileWriter writer = new FileWriter(gpxfile);
                    writer.append(output);
                    writer.flush();
                    writer.close();
                    Toast.makeText(Analysis.this, "Saved your text", Toast.LENGTH_LONG).show();
                } catch (Exception e) { }

            }

        });

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mTextView = (TextView) findViewById(R.id.text3);

        // Enables Always-on
        setAmbientEnabled();
        listener2 = new SensorEventListener() {
            public void onSensorChanged(SensorEvent var1) {
            }

            public void onAccuracyChanged(Sensor var1, int var2) {
            }

        };

//        mSensorManager.registerListener(listener2, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_UI);

        listener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                try {
                    gcd  = new Geocoder(Analysis.this, Locale.getDefault());
                    if (location != null)
                    address = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (firstRun) {
                    String first;
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    last = location;
                    firstRun = false;
                }
                else {
                    //  distance = distanceFormula(latitude, location.getLatitude(), longitude, location.getLatitude());
                    distance = location.distanceTo(last);

                    if (distanceRange < distance) {

                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
            }


        };




        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        mSensorManager.registerListener(listener2, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(listener2, mSensorManager.getDefaultSensor(TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);

    }
//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//        manager.removeUpdates(listener);
//        mSensorManager.unregisterListener(listener2);
//    }
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Permission is not granted
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//
//        } else {
//            // Permission has already been granted
//            trigger = true;
//        }
//        ;
//
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            // Permission is not granted
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
//                // No explanation needed; request the permission
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }
//
//        } else {
//            // Permission has already been granted
//            trigger2 = true;
//        }
//        ;
//        if (trigger) {
//            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
//            // manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
//            mSensorManager.registerListener(listener2, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_UI);
//            mSensorManager.registerListener(listener2, mSensorManager.getDefaultSensor(TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_UI);
//        }
//    }
}
