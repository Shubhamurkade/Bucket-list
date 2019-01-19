package com.example.mshack.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.mshack.R;

public class AddVicinityFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText reminderEditText;
    private Button nextButton;
    private SeekBar seekDistanceBar;
    private int distanceSelected;

    public AddVicinityFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddVicinityFragment newInstance() {
        AddVicinityFragment fragment = new AddVicinityFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_add_vicinity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reminderEditText = view.findViewById(R.id.reminder_text_et);
        nextButton = view.findViewById(R.id.next_bt);
        seekDistanceBar = view.findViewById(R.id.distance_sb);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick(v);
            }
        });

        seekDistanceBar.setMax(20);
        seekDistanceBar.setProgress(0);
        seekDistanceBar.incrementProgressBy(2);
        seekDistanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast toast = Toast.makeText(getContext(), "selected: "+progress, Toast.LENGTH_SHORT);
                toast.show();
                distanceSelected = progress;
            }
        } );
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

    public void onNextClick(View view){
        String reminderText = reminderEditText.getText().toString();
        if(reminderText.matches("")){
            Toast toast = Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment mapFragment = new SelectLocationFragment();
            //((SelectLocationFragment) mapFragment).setFragmentFields(reminderText, distanceSelected);
            transaction.replace(R.id.frament_view, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
