package com.example.mshack;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.mshack.Fragments.AddVicinityFragment;
import com.example.mshack.Fragments.SelectLocationFragment;


public class LoadTextActivity extends AppCompatActivity implements AddVicinityFragment.OnFragmentInteractionListener, SelectLocationFragment.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener {

    private DrawerLayout mMainDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_text_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment vicinityFragment = new AddVicinityFragment();
        transaction.replace(R.id.frament_view, vicinityFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
