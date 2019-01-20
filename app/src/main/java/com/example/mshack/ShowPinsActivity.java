package com.example.mshack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mshack.Utillities.ReminderNetworkUtils;
import com.mmi.MapView;
import com.mmi.MapmyIndiaMapView;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ShowPinsActivity extends AppCompatActivity {

    MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pins);

        MapmyIndiaMapView mapMyIndiaMapView = (MapmyIndiaMapView)  findViewById(R.id.mapView);
        mapView = mapMyIndiaMapView.getMapView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String username = "Nishanth";
        List<ReminderNetworkUtils.ReminderListResp.Remainder> reminders = Collections.emptyList();
        try {
            reminders = new ReminderAsyncTask().execute(username).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
