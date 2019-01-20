package com.example.mshack;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mshack.Utillities.ReminderNetworkUtils;
import com.mmi.MapView;
import com.mmi.MapmyIndiaMapView;
import com.mmi.layers.BasicInfoWindow;
import com.mmi.layers.InfoWindow;
import com.mmi.layers.Marker;
import com.mmi.layers.MarkerInfoWindow;
import com.mmi.util.GeoPoint;

import java.util.Collections;
import java.util.Iterator;
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

        Iterator<ReminderNetworkUtils.ReminderListResp.Remainder> iterator = reminders.iterator();

        while(iterator.hasNext()){
            ReminderNetworkUtils.ReminderListResp.Remainder curr = iterator.next();
            GeoPoint geoPoint = new GeoPoint(Double.parseDouble(curr.getLatitude()), Double.parseDouble(curr.getLongitude()));
            Marker marker= new Marker(mapView);
            BasicInfoWindow infoWindow = new BasicInfoWindow(R.layout.tooltip, mapView);
            marker.setPosition(geoPoint);
            marker.setDescription(curr.getTitle());
            marker.setInfoWindow(infoWindow);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            GeoPoint geoPointCenter = new GeoPoint(Double.parseDouble(curr.getLatitude()) +8 , Double.parseDouble(curr.getLongitude()) -3);
            mapView.setCenter(geoPointCenter);
            mapView.getOverlays().add(marker);
            mapView.invalidate();
        }
    }


}
