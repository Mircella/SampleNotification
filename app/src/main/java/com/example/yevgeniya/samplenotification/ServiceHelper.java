package com.example.yevgeniya.samplenotification;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;

public class ServiceHelper extends Service {
    public ServiceHelper() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher);
        Notification notification=builder.build();
        startForeground(777, notification);
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher);
        startForeground(777,builder2.build());
        stopForeground(true);
        return super.onStartCommand(intent, flags, startId);
    }
}
