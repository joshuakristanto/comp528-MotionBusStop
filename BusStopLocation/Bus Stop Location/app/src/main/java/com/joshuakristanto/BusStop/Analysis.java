package com.joshuakristanto.BusStop;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.Date;


public class Analysis extends WearableActivity {
    private TextView mTextView;
    SensorManager mSensorManager;
    SensorEventListener listener2;
    LocationManager manager;
    LocationListener listener;

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

    }
}
