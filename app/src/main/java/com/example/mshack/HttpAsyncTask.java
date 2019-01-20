package com.example.mshack;

import android.os.AsyncTask;

import com.example.mshack.Utillities.NetworkUtils;

public class HttpAsyncTask extends AsyncTask<String, Void, NetworkUtils.AutoSuggestResp> {

    private Exception exception;

    protected NetworkUtils.AutoSuggestResp doInBackground(String... urls) {
        try {
            return NetworkUtils.buildUrlForOAuth(urls[0]);

        } catch (Exception e) {
            this.exception = e;

            return null;
        }
    }

    protected void onPostExecute(NetworkUtils.AutoSuggestResp resp) {
        // TODO: check this.exception
        // TODO: do something with the feed

    }


}
