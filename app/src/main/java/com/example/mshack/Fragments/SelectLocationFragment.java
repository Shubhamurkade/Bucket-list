package com.example.mshack.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.mshack.HttpAsyncTask;
import com.example.mshack.R;
import com.example.mshack.Utillities.NetworkUtils;
import com.mmi.MapView;
import com.mmi.MapmyIndiaMapView;
import com.mmi.events.MapListener;
import com.mmi.events.ScrollEvent;
import com.mmi.events.ZoomEvent;
import com.mmi.layers.UserLocationOverlay;
import com.mmi.layers.location.GpsLocationProvider;
import com.mmi.services.account.MapmyIndiaAccountManager;
import com.mmi.services.api.Place;
import com.mmi.services.api.PlaceResponse;
import com.mmi.services.api.ServicesException;
import com.mmi.services.api.autosuggest.MapmyIndiaAutosuggest;
import com.mmi.services.api.autosuggest.model.AutoSuggestAtlasResponse;
import com.mmi.services.api.autosuggest.model.ELocation;
import com.mmi.services.api.eloc.MapmyIndiaELoc;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectLocationFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private class mapFields{
        public EditText placeEditText;
        public Button searchButton;
        public Spinner resultsSpinner;
    }

    private class userData{
        public String responseText;
        public int distance;
    }

    private userData userDataInstance;
    private mapFields mapFieldsInstance;
    private MapView mapView;
    private OnFragmentInteractionListener mListener;
    private ELocation elocSelected;
    List<ELocation> locs;

    public SelectLocationFragment() {
        // Required empty public constructor
    }


    public static SelectLocationFragment newInstance() {
        SelectLocationFragment fragment = new SelectLocationFragment();
        return fragment;
    }

    public void setFragmentFields(String reminderText, int distance){
        userDataInstance = new userData();
        userDataInstance.responseText = reminderText;
        userDataInstance.distance= distance;
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

        UserLocationOverlay mLocationOverlay;
        mLocationOverlay = new UserLocationOverlay(new GpsLocationProvider(getContext()), mapView);
        mLocationOverlay.enableMyLocation();
        //mLocationOverlay.setCurrentLocationResId(R.drawable.ic_launcher);
        mapView.getOverlays().add(mLocationOverlay);
        mapView.invalidate();

        mapFieldsInstance = new mapFields();
        mapFieldsInstance.placeEditText = view.findViewById(R.id.search_et);
        mapFieldsInstance.resultsSpinner = view.findViewById(R.id.results_spinner);
        mapFieldsInstance.searchButton = view.findViewById(R.id.search_bt);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFieldsInstance.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HttpAsyncTask().execute("");
            }
        });
        getSuggestions();
    }

    private void getSuggestions(){

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        elocSelected = locs.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
