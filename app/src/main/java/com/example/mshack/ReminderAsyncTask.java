package com.example.mshack;

import android.os.AsyncTask;

import com.example.mshack.Utillities.ReminderNetworkUtils;

import java.util.Collections;
import java.util.List;

public class ReminderAsyncTask extends AsyncTask<String, Void, List<ReminderNetworkUtils.ReminderListResp.Remainder>> {

    private Exception exception;

    protected List<ReminderNetworkUtils.ReminderListResp.Remainder> doInBackground(String... username) {
        try {
            String access_token = ReminderNetworkUtils.getJWTToken(username[0]);
            return ReminderNetworkUtils.buildUrlForServerFetch(access_token);

        } catch (Exception e) {
            this.exception = e;

            return Collections.emptyList();
        }
    }

    protected void onPostExecute(List<ReminderNetworkUtils.ReminderListResp.Remainder> feed) {
// TODO: check this.exception
// TODO: do something with the feed'

    }
}