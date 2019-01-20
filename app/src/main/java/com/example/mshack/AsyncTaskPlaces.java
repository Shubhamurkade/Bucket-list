package com.example.mshack;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mshack.Fragments.SelectLocationFragment;
import com.example.mshack.Utillities.NetworkUtils;

import java.util.Iterator;
import java.util.List;

public class AsyncTaskPlaces extends AsyncTask<SelectLocationFragment.mapFields, Void, SelectLocationFragment.mapFields> {

private Exception exception;

    @Override
    protected SelectLocationFragment.mapFields doInBackground(SelectLocationFragment.mapFields... mapFields) {

        List<NetworkUtils.AutoSuggestResp.Places> places = mapFields[0].resp.getSuggestedLocations();

        Iterator<NetworkUtils.AutoSuggestResp.Places> it = places.iterator();
        int i=0;
        while(it.hasNext())
        {
            NetworkUtils.AutoSuggestResp.Places curr = it.next();
            String address = curr.getPlaceAddress();
            String placeName = curr.getPlaceName();


            //mapFields[0].spinnerAdapter.add(placeName+"\\n"+address);
            mapFields[0].placeAppended.add(placeName+"\\n"+address);
            mapFields[0].lats.add(curr.getLatitude());
            mapFields[0].longs.add(curr.getLongitude());
            Log.i("PLAE:", placeName);
            i++;
        }
        return mapFields[0];
    }

protected void onPostExecute(NetworkUtils.AutoSuggestResp resp) {
        // TODO: check this.exception
        // TODO: do something with the feed

        }



}