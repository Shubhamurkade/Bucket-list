package com.example.mshack;

import android.os.AsyncTask;

import com.example.mshack.Utillities.AddRemainderRequest;
import com.example.mshack.Utillities.ReminderNetworkUtils;

import java.util.Collections;
import java.util.List;

public class ReminderAddAsyncTask extends AsyncTask<String, Void, Void> {

    private Exception exception;

    protected Void doInBackground(String... username) {
        try {
            String access_token = ReminderNetworkUtils.getJWTToken(username[0]);
            AddRemainderRequest addRemainderRequest = new AddRemainderRequest();
            addRemainderRequest.setId((int)System.currentTimeMillis()/1000);
            addRemainderRequest.setText(username[1]);
            addRemainderRequest.setTitle(username[2]);
            addRemainderRequest.setLatitude(username[3]);
            addRemainderRequest.setLongitude(username[4]);
            addRemainderRequest.setPlace(username[5]);
            addRemainderRequest.setRadius(Integer.parseInt(username[6]));
            ReminderNetworkUtils.buildUrlForServerAdd(addRemainderRequest, access_token);

        } catch (Exception e) {
            this.exception = e;

        }
        return null;
    }

    protected void onPostExecute(List<ReminderNetworkUtils.ReminderListResp.Remainder> feed) {
// TODO: check this.exception
// TODO: do something with the feed'

    }
}