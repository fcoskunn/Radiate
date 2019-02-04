package com.fc.radiate.NotificationsAndReminders;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.fc.radiate.Activities.MainActivity;
import com.fc.radiate.R;


public class NotificationUtils {
    private static final int RADIATE_REMINDER_NOTIFICATION_ID = 1187;
    private static final int RADIATE_REMINDER_PENDING_INTENT_ID = 2347;
    private static final String RADIATE_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    private static final int ACTION_OPEN_APP_INTENT_ID = 10;
    private static final int ACTION_COUNTRY_LIST_INTENT_ID = 41;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                RADIATE_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void remindUserDiscoverRadio(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    RADIATE_NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, RADIATE_NOTIFICATION_CHANNEL_ID);

        notificationBuilder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notif2Title))
                .setContentText(context.getString(R.string.notif2Body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notif2Body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)
                .addAction(openCountryListAction(context))
                .addAction(ignoreReminderAction2(context));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(RADIATE_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void remindUserListenRadio(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    RADIATE_NOTIFICATION_CHANNEL_ID,
                    "Primary",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, RADIATE_NOTIFICATION_CHANNEL_ID);

        notificationBuilder
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notif1Title))
                .setContentText(context.getString(R.string.notif1Body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notif1Body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true)
                .addAction(openRadiateAction(context))
                .addAction(ignoreReminderAction(context));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(RADIATE_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher_round);
        return largeIcon;
    }

    private static NotificationCompat.Action openRadiateAction(Context context) {
        Intent openAppIntent = new Intent(context, RadiateReminderIntentService.class);
        openAppIntent.setAction(ReminderTask.ACTION_OPEN_APP);
        PendingIntent openApp = PendingIntent.getService(
                context, ACTION_OPEN_APP_INTENT_ID,
                openAppIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action goToApp = new NotificationCompat.Action(R.drawable.radio_tower,
                "Go and Radiate!",
                openApp);

        return goToApp;
    }

    private static NotificationCompat.Action openCountryListAction(Context context) {
        Intent openAppIntent = new Intent(context, RadiateReminderIntentService.class);
        openAppIntent.setAction(ReminderTask.ACTION_OPEN_COUNTRY_LIST);
        PendingIntent openApp = PendingIntent.getService(
                context, ACTION_COUNTRY_LIST_INTENT_ID,
                openAppIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Action goToApp = new NotificationCompat.Action(R.drawable.radio_tower,
                "Discover Countries!",
                openApp);

        return goToApp;
    }

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreReminderIntent = new Intent(context,
                RadiateReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTask.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.btn_notification_collapse,
                "No, thanks.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    private static NotificationCompat.Action ignoreReminderAction2(Context context) {
        Intent ignoreReminderIntent = new Intent(context,
                RadiateReminderIntentService.class);
        ignoreReminderIntent.setAction(ReminderTask.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(R.drawable.btn_notification_collapse,
                "Not now.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }


}

