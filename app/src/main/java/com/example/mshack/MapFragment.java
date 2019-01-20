package com.example.mshack;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mshack.Utillities.AddRemainderRequest;
import com.mmi.MapView;
import com.mmi.MapmyIndiaMapView;
import com.mmi.events.MapListener;
import com.mmi.events.ScrollEvent;
import com.mmi.events.ZoomEvent;
import com.mmi.layers.Marker;
import com.mmi.layers.UserLocationOverlay;
import com.mmi.layers.location.GpsLocationProvider;
import com.mmi.util.GeoPoint;


public class MapFragment extends Fragment {


    private MapView mapView;
    private String lat;
    private String longi;
    private String placeName;
    private int distance;
    private String reminderText;
    private FloatingActionButton addToDbFab;
    private String reminderTitle;

    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = ((MapmyIndiaMapView) view.findViewById(R.id.mapView)).getMapView();
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addToDbFab = view.findViewById(R.id.fab);
        addToDbFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new ReminderAddAsyncTask().execute("Nishanth", reminderTitle, reminderText, lat, longi, placeName, Integer.toString(distance));

                Intent intent = new Intent(getContext(), listOfRemindersActivity.class);
                startActivity(intent);
            }
        });
        //mapView = view.findViewById(R.id.mapView);
        mapView.setMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                return false;
            }   @Override
            public boolean onZoom(ZoomEvent event) {
//this method captures the zoom event by the user.
                return false;
            } });

        UserLocationOverlay mLocationOverlay;
        mLocationOverlay = new UserLocationOverlay(new GpsLocationProvider(getContext()), mapView);
        mLocationOverlay.enableMyLocation();
        //mLocationOverlay.setCurrentLocationResId(R.drawable.ic_launcher);
        mapView.getOverlays().add(mLocationOverlay);
        mapView.invalidate();
        //mapView = view.findViewById(R.id.mapView);
        Marker marker= new Marker(mapView);
        GeoPoint geoPoint = new GeoPoint(Double.parseDouble(lat), Double.parseDouble(longi));
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        GeoPoint geoPointCenter = new GeoPoint(18.67 , 74.8849);
        mapView.setCenter(geoPointCenter);
        mapView.getOverlays().add(marker);
        mapView.invalidate();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setFragmentFields(String place, String lat, String longi, int distance, String reminderText, String reminderTitle){
    this.lat = lat;
    this.longi = longi;
    this.placeName = place;
    this.distance = distance;
    this.reminderText = reminderText;
    this.reminderTitle = reminderTitle;
    }


}
