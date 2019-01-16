package com.example.mshack;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mshack.Fragments.AddCityFragment;
import com.example.mshack.Fragments.AddVicinityFragment;
import com.example.mshack.Fragments.SelectLocationFragment;

public class MainActivity extends AppCompatActivity implements AddVicinityFragment.OnFragmentInteractionListener, SelectLocationFragment.OnFragmentInteractionListener {

    private DrawerLayout mMainDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mMainDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        menuItem.setChecked(true);
                        setFragmentView(menuItem);

                        return true;
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment defaultViewFragment = new AddVicinityFragment();
        transaction.replace(R.id.frament_view, defaultViewFragment);
        transaction.commit();
    }

    private void setFragmentView(MenuItem menuItem){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment chooseFragment;
        switch (menuItem.getItemId()){
            case R.id.nav_add_vicinity:
                chooseFragment = AddVicinityFragment.newInstance();
                break;
            case R.id.nav_add_city:
                chooseFragment = AddCityFragment.newInstance();
                break;

            default:
                chooseFragment = new AddVicinityFragment();
                break;
        }

        transaction.replace(R.id.frament_view, chooseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMainDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
