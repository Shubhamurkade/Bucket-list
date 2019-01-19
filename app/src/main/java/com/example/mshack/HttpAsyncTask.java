package com.example.mshack;

import android.os.AsyncTask;

import com.example.mshack.Utillities.NetworkUtils;

public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
            return NetworkUtils.buildUrlForOAuth(urls[0]);

        } catch (Exception e) {
            this.exception = e;

            return null;
        }
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
