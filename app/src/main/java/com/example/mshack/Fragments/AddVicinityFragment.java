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
import android.widget.Toast;

import com.example.mshack.R;

public class AddVicinityFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText reminderEditText;
    private EditText reminderPhoneNumber;
    private Button nextButton;

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
        reminderPhoneNumber = view.findViewById(R.id.reminder_number_et);
        nextButton = view.findViewById(R.id.next_bt);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick(v);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onNextClick(View view){
        String reminderText = reminderEditText.getText().toString();
        String reminderNumber = reminderPhoneNumber.getText().toString();
        if(reminderNumber.matches("") || reminderNumber.matches("")){
            Toast toast = Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            int reminderPhoneNumber = Integer.parseInt(reminderNumber);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment mapFragment = new SelectLocationFragment();
            ((SelectLocationFragment) mapFragment).setFragmentFields(reminderText, reminderPhoneNumber);
            transaction.replace(R.id.frament_view, mapFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}
