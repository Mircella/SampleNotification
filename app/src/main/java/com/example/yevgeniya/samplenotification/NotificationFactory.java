package com.example.yevgeniya.samplenotification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Yevgeniya on 01.12.2017.
 */

public class NotificationFactory {
    private static NotificationFactory factory;
    private String title;
    private String content;
    private PendingIntent pIntent;
    private Context context;
    private NotificationFactory(Context context,String title,String content,PendingIntent pIntent){
        this.title = title;
        this.content = content;
        this.context = context;
        this.pIntent = pIntent;
    }
    public static NotificationFactory getInstance(Context context,String title,String content,PendingIntent pIntent){
        if(factory==null) factory = new NotificationFactory(context,title,content,pIntent);
        return factory;
    }
    public Notification createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("Notification");
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        builder.setContentIntent(pIntent);
        return  builder.build();
    }



}
