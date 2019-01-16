package com.example.mshack.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.mshack.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class SelectLocationFragment extends Fragment {


    private WebView webViewMap;
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
        return inflater.inflate(R.layout.fragment_select_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        webViewMap = view.findViewById(R.id.webview_map);
        webViewMap.getSettings().setJavaScriptEnabled(true);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        evaluateJsCodeMap();
    }

    private boolean evaluateJsCodeMap(){

        File jsCode = new File("mapJs.html");
        byte [] data = null;
        try{
            FileInputStream inputStream = new FileInputStream(jsCode);
            data = new byte[(int)jsCode.length()];
            inputStream.read(data);
            inputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try{
            String jsCodeString = new String(data, "UTF-8");
            webViewMap.loadUrl(jsCodeString);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return true;
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
