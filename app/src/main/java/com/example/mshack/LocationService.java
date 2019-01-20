package com.example.mshack;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.mshack.Utillities.ReminderNetworkUtils.ReminderListResp.Remainder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LocationService extends Service{
    String TAG ="locationservice";
    public static final String BROADCAST_ACTION = "Hello World";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;

    Context mContext;

    Intent intent;
    int counter = 0;

    private NotificationManager mNotificationManager;


    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
        Log.i(TAG, "onCreate: ");
        mContext = this.getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStart: ");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return START_STICKY;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, (LocationListener) listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }
    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        locationManager.removeUpdates(listener);
    }

    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }
    public class MyLocationListener implements LocationListener
    {

        public void onLocationChanged(final Location loc) {
            Log.i(TAG, "Location changed " + loc);
            if (isBetterLocation(loc, previousBestLocation))
            {
                String username = "Nishanth";
                List<Remainder> reminders = Collections.emptyList();
                try
                {
                    reminders = new ReminderAsyncTask().execute(username).get();
                    List<Remainder> NearByReminders = Collections.emptyList();
                    NearByReminders = CheckNearByReminders(reminders, loc);
                    int size = NearByReminders.size();
                    for(int i = 0; i<size; i++)
                    {
                        Remainder r = NearByReminders.get(i);
                        String NotificationText = r.getText() + "  at  " + r.getPlace();
                        PushANotification(Integer.parseInt(r.getId()), r.getTitle(), NotificationText);
                    }
                }
                catch (InterruptedException | ExecutionException e)
                {
                    //e.printStackTrace();
                }
                String details = "Latitude" + loc.getLatitude() + "\n" + "Longitude" + loc.getLongitude();

                loc.getLatitude();
                loc.getLongitude();
                //intent.putExtra("Latitude", loc.getLatitude());
                //intent.putExtra("Longitude", loc.getLongitude());
                //intent.putExtra("Provider", loc.getProvider());
                //sendBroadcast(intent);
            }
        }

        List<Remainder> CheckNearByReminders(List<Remainder> reminders, Location loc)
        {
            List<Remainder> NearbyReminders = new ArrayList<Remainder>();
            final int size = reminders.size();
            for (int i = 0; i < size; i++)
            {
                Remainder r = reminders.get(i);
                if(CheckDistanceFromCurrentLocation(loc, r))
                {
                    NearbyReminders.add(r);
                }
            }
            return NearbyReminders;
        }

        boolean CheckDistanceFromCurrentLocation(Location loc, Remainder r)
        {
            Location loc1 = new Location("");
            loc1.setLatitude(Double.parseDouble(r.getLatitude()));
            loc1.setLongitude(Double.parseDouble(r.getLongitude()));

            float distanceInMeters = loc1.distanceTo(loc);
            Log.i(TAG, "CheckDistanceFromCurrentLocation: " + distanceInMeters/1000.0);
            if(distanceInMeters/1000.0 <= Double.parseDouble(r.getRadius())) {
                return true;
            }
            return false;
        }
        void PushANotification(int id, String reminderTitle, String reminderDetails)
        {
            String CHANNEL_ID = "BuketList";

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setSmallIcon(R.drawable.mapbox_compass_icon)
                    .setContentTitle(reminderTitle)
                    //.setContentText("reminderTitle")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(reminderDetails))
                    .setPriority(NotificationCompat.PRIORITY_MAX);

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            mNotificationManager =
                    (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(CHANNEL_ID);
            mNotificationManager.notify(id, mBuilder.build());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }


        public void onProviderEnabled(String provider)
        {
            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }




    }
}