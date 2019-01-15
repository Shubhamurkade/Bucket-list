package com.example.mshack;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mshack.Fragments.AddCityFragment;
import com.example.mshack.Fragments.AddVicinityFragment;

public class MainActivity extends AppCompatActivity implements AddVicinityFragment.OnFragmentInteractionListener{

    private DrawerLayout mMainDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                chooseFragment = new AddCityFragment();
        }

        transaction.replace(R.id.frament_view, chooseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
