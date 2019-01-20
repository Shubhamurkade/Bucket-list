package com.example.mshack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.mshack.Utillities.ReminderNetworkUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class listOfRemindersActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ReminderNetworkUtils.ReminderListResp.Remainder> reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_reminders);

        mRecyclerView = (RecyclerView) findViewById(R.id.LORRecyclerView);

// use this setting to improve performance if you know that changes
// in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

// use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        startService(new Intent(this.getApplicationContext(), LocationService.class));

//reminders.add(new ReminderItem("1", "100", "120", "Koramangala", "2000","Ice Cream", "Remember to Eat ice cream in Corner house", "2019_01_19"));

        String username = "Nishanth";
        List<ReminderNetworkUtils.ReminderListResp.Remainder> reminders = Collections.emptyList();
        try {
            reminders = new ReminderAsyncTask().execute(username).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        mAdapter = new LORReminderListRecyclerViewAdapter(reminders, getBaseContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void fabClick(View view) {
        Intent myIntent = new Intent(listOfRemindersActivity.this, LoadTextActivity.class);
        listOfRemindersActivity.this.startActivity(myIntent);
    }

    public void showPinsClick(View view){
        Intent myIntent = new Intent(this, ShowPinsActivity.class);
        listOfRemindersActivity.this.startActivity(myIntent);
    }

}