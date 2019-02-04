package com.fc.radiate.NotificationsAndReminders;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class ReminderUtilities {

    private static final int REMINDER_INTERVAL_ONEDAY = 1;
    private static final int REMINDER_INTERVAL_TWODAY = 2;
    private static final int REMINDER_INTERVAL_MILLIS_ONE = (int) (TimeUnit.DAYS.toMillis(REMINDER_INTERVAL_ONEDAY));
    private static final int REMINDER_INTERVAL_MILLIS_TWO = (int) (TimeUnit.DAYS.toMillis(REMINDER_INTERVAL_TWODAY));
    private static boolean sInitialized;
    private static boolean sInitialized2;


    synchronized public static void scheduleRadiateReminder(Context context) {
        if (!sInitialized) {
            JobScheduler scheduler = (JobScheduler) context
                    .getSystemService(JOB_SCHEDULER_SERVICE);

            ComponentName componentName = new ComponentName(context,
                    RadiateReminderFirebaseJobService.class);

            JobInfo jobInfoObj = new JobInfo.Builder(1032, componentName)
                    .setPeriodic(REMINDER_INTERVAL_MILLIS_ONE)
                    .setPersisted(true)
                    .setRequiresCharging(true)
                    .build();

            int res = scheduler.schedule(jobInfoObj);
            if (res == JobScheduler.RESULT_SUCCESS) {
                Log.v("JobScheduler", "WORKS");
            } else Log.v("JobScheduler", "FAILS");
            sInitialized = true;
        }
        if (!sInitialized2) {
            JobScheduler scheduler = (JobScheduler) context
                    .getSystemService(JOB_SCHEDULER_SERVICE);

            ComponentName componentName = new ComponentName(context, RadiateCountryDiscoverFirebaseJobService.class);

            JobInfo jobInfoObj = new JobInfo.Builder(1314, componentName)
                    .setPeriodic(REMINDER_INTERVAL_MILLIS_TWO)
                    .setPersisted(true)
                    .setRequiresCharging(true)
                    .build();

            int res = scheduler.schedule(jobInfoObj);
            if (res == JobScheduler.RESULT_SUCCESS) {
                Log.v("JobScheduler", "WORKS2");
            } else Log.v("JobScheduler", "FAILS2");
            sInitialized2 = true;
        }
    }
}