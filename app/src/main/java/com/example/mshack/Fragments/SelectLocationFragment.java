package com.example.mshack.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mshack.R;
import com.mmi.MapView;
import com.mmi.MapmyIndiaMapView;
import com.mmi.events.MapListener;
import com.mmi.events.ScrollEvent;
import com.mmi.events.ZoomEvent;
import com.mmi.util.GeoPoint;

public class SelectLocationFragment extends Fragment {


    //private WebView webViewMap;
    private MapView mapView;
    private OnFragmentInteractionListener mListener;
    private String reminderText;
    private int reminderPhoneNumber;

    public SelectLocationFragment() {
        // Required empty public constructor
    }


    public static SelectLocationFragment newInstance() {
        SelectLocationFragment fragment = new SelectLocationFragment();
        return fragment;
    }

    public void setFragmentFields(String reminderText, int reminderPhone){
        reminderText = reminderText;
        reminderPhoneNumber = reminderPhone;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_location, container, false);
        mapView = ((MapmyIndiaMapView) view.findViewById(R.id.mapView)).getMapView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        //webViewMap = view.findViewById(R.id.webview_map);
        //webViewMap.getSettings().setJavaScriptEnabled(true);
        mapView.setMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent scrollEvent) {
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent zoomEvent) {
                return false;
            }
        });

        GeoPoint geoPoint = new GeoPoint(48.8583, 22.944);
        mapView.setCenter(geoPoint);
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



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
