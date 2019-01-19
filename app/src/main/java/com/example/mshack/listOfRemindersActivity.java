package com.example.mshack;

//import android.net.http.RequestQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mshack.dummy.ReminderItemContent;
import com.example.mshack.dummy.ReminderItemContent.ReminderItem;

import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import android.net.http.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class listOfRemindersActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        // specify an adapter (see also next example)
        //String[] mDataset = {"1","2","3"};
        List<ReminderItem> reminders = new ArrayList<ReminderItem>();
        reminders.add(new ReminderItem("0", "100", "120", "Koramangala", "2000",
                "Ice Cream", "Remember to Eat ice cream in Corner house", "2019_01_19"));
        /*reminders.add(new ReminderItem("1", "100", "120", "Koramangala", "2000","Ice Cream", "Remember to Eat ice cream in Corner house", "2019_01_19"));
        reminders.add(new ReminderItem("2", "100", "120", "Koramangala", "2000","Ice Cream", "Remember to Eat ice cream in Corner house", "2019_01_19"));
        reminders.add(new ReminderItem("3", "100", "120", "Koramangala", "2000","Ice Cream", "Remember to Eat ice cream in Corner house", "2019_01_19"));
        reminders.add(new ReminderItem("4", "100", "120", "Koramangala", "2000","Ice Cream", "Remember to Eat ice cream in Corner house", "2019_01_19"));
        */
        //reminders = GetRemindersFromServer();


        //LORReminderListFragment.OnListFragmentInteractionListener mListener;
        mAdapter = new LORReminderListRecyclerViewAdapter(reminders);
        mRecyclerView.setAdapter(mAdapter);
    }
    /*
    List<ReminderItem> GetRemindersFromServer()
    {
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        List<ReminderItem> reminders = new ArrayList<ReminderItem>();
        reminders.add(new ReminderItem("0", "100", "120", "Koramangala", "2000",
                "Ice Cream", "Remember to Eat ice cream in Corner house", "2019_01_19"));

        return  reminders;
    }*/

}
