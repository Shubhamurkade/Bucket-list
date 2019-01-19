package com.example.mshack.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mshack.R;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelectLocationFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    //private WebView webViewMap;
    private MapView mapView;
    private OnFragmentInteractionListener mListener;
    private String reminderText;
    private int reminderPhoneNumber;
    private Spinner resultsSpinner;
    private ELocation elocSelected;
    List<ELocation> locs;

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

        UserLocationOverlay mLocationOverlay;
        mLocationOverlay = new UserLocationOverlay(new GpsLocationProvider(getContext()), mapView);
        mLocationOverlay.enableMyLocation();
        //mLocationOverlay.setCurrentLocationResId(R.drawable.ic_launcher);
        mapView.getOverlays().add(mLocationOverlay);
        mapView.invalidate();
    }

    @Override
    public void onStart() {
        super.onStart();
        getSuggestions();
    }

    private void getSuggestions(){
        MapmyIndiaAccountManager test = MapmyIndiaAccountManager.getInstance();
        try{
            new MapmyIndiaAutosuggest.Builder<>()
                    .setBridge(false)
                    .setLocation(28,77)
                    .setQuery("mmi000")
                    .build()
                    .enqueueCall(new Callback<AutoSuggestAtlasResponse>() {
                        @Override
                        public void onResponse(Call<AutoSuggestAtlasResponse> call, Response<AutoSuggestAtlasResponse> response) {
                            locs = response.body().getSuggestedLocations();

                            Iterator<ELocation> it = locs.iterator();

                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
                            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            resultsSpinner.setAdapter(spinnerAdapter);

                            while(it.hasNext())
                            {

                                spinnerAdapter.add(it.next().placeName);
                                spinnerAdapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onFailure(Call<AutoSuggestAtlasResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
        catch (Exception e) {
        e.printStackTrace();
        }
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
