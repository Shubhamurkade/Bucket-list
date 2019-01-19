package com.example.mshack;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class locationScannerService extends JobService
{
    private static final String TAG = "locationScannerService";
    @Override
    public boolean onStartJob(JobParameters params)
    {
        Log.i(TAG, "onStartJob:");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        Log.i(TAG, "onStopJob:");
        return false;
    }
}
