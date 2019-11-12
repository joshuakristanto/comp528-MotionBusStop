package com.joshuakristanto.BusStop;


import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;


public class BusStopPredict extends WearableActivity{
    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_stop);

        mTextView = (TextView) findViewById(R.id.text2);

        // Enables Always-on
        setAmbientEnabled();
    }

}
