package com.example.yevgeniya.samplenotification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Yevgeniya on 01.12.2017.
 */

public class Sender extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra("notification");
        boolean notOn = intent.getBooleanExtra("notOn",true);
        if(notOn)
        {
            notificationManager.notify(1, notification);
        }
    }


}
