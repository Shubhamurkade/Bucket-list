package com.example.mshack.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.mshack.AsyncTaskPlaces;
import com.example.mshack.HttpAsyncTask;
import com.example.mshack.MapFragment;
import com.example.mshack.R;
import com.example.mshack.Utillities.NetworkUtils;
import com.mmi.MapView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class SelectLocationFragment extends Fragment {


    public class mapFields{
        public EditText placeEditText;
        public Button searchButton;
        public Spinner resultsSpinner;
        public ArrayAdapter<String> spinnerAdapter;
        public List<String> placeAppended = new ArrayList<>();
        public List<String> lats = new ArrayList<>();
        public List<String> longs = new ArrayList<>();
        public String textSearch;
        public NetworkUtils.AutoSuggestResp resp;
    }

    private class userData{
        public String responseText;
        public int distance;
        public String responseTitle;
    }


    NetworkUtils.AutoSuggestResp arr;
    private userData userDataInstance;
    private mapFields mapFieldsInstance;
    private MapView mapView;
    private OnFragmentInteractionListener mListener;
    private Button nextButton;
    private int selectedPosition = 0;

    public SelectLocationFragment() {
        // Required empty public constructor
    }


    public static SelectLocationFragment newInstance() {
        SelectLocationFragment fragment = new SelectLocationFragment();
        return fragment;
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


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        //webViewMap = view.findViewById(R.id.webview_map);
        //webViewMap.getSettings().setJavaScriptEnabled(true);


        mapFieldsInstance = new mapFields();
        mapFieldsInstance.placeEditText = view.findViewById(R.id.search_et);
        mapFieldsInstance.resultsSpinner = view.findViewById(R.id.results_spinner);
        mapFieldsInstance.searchButton = view.findViewById(R.id.search_bt);

        mapFieldsInstance.spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        mapFieldsInstance.spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapFieldsInstance.resultsSpinner.setAdapter(mapFieldsInstance.spinnerAdapter);

        nextButton = view.findViewById(R.id.button);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFieldsInstance.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mapFieldsInstance.resultsSpinner.setAdapter(null);
                //mapFieldsInstance.spinnerAdapter.notifyDataSetChanged();
                try{
                    mapFieldsInstance.resultsSpinner.setAdapter(mapFieldsInstance.spinnerAdapter);
                    mapFieldsInstance.textSearch = mapFieldsInstance.placeEditText.getText().toString();
                    mapFieldsInstance.resp = new HttpAsyncTask().execute(mapFieldsInstance.textSearch).get();
                    new AsyncTaskPlaces().execute(mapFieldsInstance);
                    mapFieldsInstance.spinnerAdapter.clear();
                    mapFieldsInstance.spinnerAdapter.notifyDataSetChanged();
                    mapFieldsInstance.spinnerAdapter.addAll(mapFieldsInstance.placeAppended);
                    mapFieldsInstance.spinnerAdapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedPlace = mapFieldsInstance.placeAppended.get(selectedPosition);
                String lat = mapFieldsInstance.lats.get(selectedPosition);
                String longi = mapFieldsInstance.longs.get(selectedPosition);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment mapFragment = new MapFragment();
                ((MapFragment) mapFragment).setFragmentFields(selectedPlace, lat, longi, userDataInstance.distance, userDataInstance.responseText, userDataInstance.responseTitle);
                transaction.replace(R.id.frament_view, mapFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mapFieldsInstance.resultsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedPosition = position;
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
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

    public void setFragmentFields(String reminderTitle, String reminderText, int distance)
    {
        userDataInstance = new userData();
        userDataInstance.responseText = reminderText;
        userDataInstance.distance = distance;
        userDataInstance.responseTitle = reminderTitle;
    }
}
