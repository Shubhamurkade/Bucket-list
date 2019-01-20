package com.example.mshack;

import android.os.AsyncTask;

import com.example.mshack.Utillities.ReminderNetworkUtils;

import java.util.Collections;
import java.util.List;

public class ReminderDeleteAsyncTask extends AsyncTask<String, Void, Void> {

    private Exception exception;

    protected Void doInBackground(String... username) {
        try {
            String access_token = ReminderNetworkUtils.getJWTToken(username[0]);
            ReminderNetworkUtils.buildUrlForServerDelete(access_token , username[1]);

        } catch (Exception e) {
            this.exception = e;

// return Collections.emptyList();
        }
        return null;
    }

    protected void onPostExecute() {
// TODO: check this.exception
// TODO: do something with the feed'

    }
}