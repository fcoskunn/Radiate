package com.fc.radiate.NotificationsAndReminders;

import android.content.Context;
import android.content.Intent;

import com.fc.radiate.Activities.MainActivity;

public class ReminderTask {
    public static final String ACTION_OPEN_APP = "open-app";
    public static final String ACTION_OPEN_COUNTRY_LIST = "open-country-list";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";

    public static void executeTask(Context context, String action) {
        if (ACTION_OPEN_APP.equals(action)) {
            openRadiate(context);
        } else if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.clearAllNotifications(context);
        } else if(ACTION_OPEN_COUNTRY_LIST.equals(action)){
            openCountryList(context);
            NotificationUtils.clearAllNotifications(context);
        }
    }

    private static void openRadiate(Context context) {
        Intent newInt = new Intent(context, MainActivity.class);
        context.startActivity(newInt);
        NotificationUtils.clearAllNotifications(context);
    }

    private static void openCountryList(Context context) {
        Intent newInt = new Intent(context, MainActivity.class);
        newInt.putExtra("FragmentToLoad", "COUNTRY");
        context.startActivity(newInt);
        NotificationUtils.clearAllNotifications(context);
    }
}
