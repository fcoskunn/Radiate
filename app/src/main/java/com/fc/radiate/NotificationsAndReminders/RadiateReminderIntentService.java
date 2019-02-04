package com.fc.radiate.NotificationsAndReminders;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class RadiateReminderIntentService extends IntentService {
    public RadiateReminderIntentService() {
        super("RadiateReminderIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        ReminderTask.executeTask(this, action);
    }
}
