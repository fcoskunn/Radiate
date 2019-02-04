package com.fc.radiate.NotificationsAndReminders;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class RadiateCountryDiscoverFirebaseJobService extends JobService {

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.v("TESTJOBSCH", "CALISTI");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Context context = RadiateCountryDiscoverFirebaseJobService.this;
                NotificationUtils.remindUserDiscoverRadio(context);
            }
        }).start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
