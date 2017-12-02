package com.example.yevgeniya.samplenotification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    SharedPreferences sp;
    NotificationFactory factory;
    Context context;
    AlarmManager alarmManager;
    ExecutorService executor;
    NotificationManager notificationManager;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newFixedThreadPool(1);
       // notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher);
        startForeground(777,builder2.build());
        Intent hideIntent = new Intent(this, ServiceHelper.class);
        startService(hideIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        String freq = intent.getStringExtra("interval");
        int freqNum = Integer.parseInt(freq);
        boolean notOn = intent.getBooleanExtra("notification",true);
        boolean soundOn = intent.getBooleanExtra("sound",true);
        boolean vibrOn = intent.getBooleanExtra("vibration",true);
        StringBuffer resultText = new StringBuffer();
        resultText.append(text+" f "+freqNum+" n "+notOn+" "+" s "+soundOn+" v "+vibrOn);
        context = getApplicationContext();

        Notification notification = createNotification(context,title,resultText.toString(),soundOn,vibrOn);
        //notificationManager.notify(1,notification);
        send(context,notification,freqNum,notOn);
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher);
        startForeground(777,builder2.build());
        Intent hideIntent = new Intent(this, ServiceHelper.class);
        startService(hideIntent);

        //MyRun run = new MyRun(context);
        //executor.execute(run);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private void sendNotification(){

    }

    private class MyRun implements Runnable{
        Notification notification;
        Context context;
        int freqNum;

        public  MyRun(Context context,Notification notification,int freqNum){
            this.context=context;
            this.notification=notification;
            this.freqNum=freqNum;
        }

        public MyRun(Notification notification){
            this.notification = notification;
        }

        @Override
        public void run() {
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent("sender");
            intent.putExtra("notification",notification);
            try {
                TimeUnit.SECONDS.sleep(5);
               sendBroadcast(intent);
            }catch (InterruptedException e){}

            //Intent intent = new Intent(getApplicationContext(),Sender.class);
            //intent.putExtra("notification",notification);
           // PendingIntent pIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
           // alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
           // alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,2000,10000,pIntent);
        }
    }

    private void send(Context context, Notification notification, int freqNum, boolean notOn){
        Intent notifIntent = new Intent(context,Sender.class);
        notifIntent.putExtra("notifID",1);
        notifIntent.putExtra("notification",notification);
        notifIntent.putExtra("notOn",notOn);
        //notifIntent.putExtra("not",notifOn);
        PendingIntent pIntent = PendingIntent.getBroadcast(context,0,notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        long futureTime = SystemClock.elapsedRealtime()+freqNum*1000;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,futureTime,freqNum*5000,pIntent);

    }

    private Notification createNotification(Context context,String title,String resultText,boolean soundOn, boolean vibrOn){
        Intent serviceIntent = new Intent(this,MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,0,serviceIntent,0);
        factory = NotificationFactory.getInstance(context,title,resultText.toString(),pIntent);
        Notification notification = factory.createNotification();
        if(soundOn&&vibrOn)
        {notification.defaults = Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE;}
        else if(soundOn&&!vibrOn)
        {notification.defaults = Notification.DEFAULT_SOUND;}
        else if(!soundOn&&vibrOn)
        {notification.defaults = Notification.DEFAULT_VIBRATE;}
        else{}
        return  notification;
    }

    /*@Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String freq = sp.getString("interval","");
        boolean notOn = sp.getBoolean("notification",true);
        boolean soundOn = sp.getBoolean("sound",true);
        boolean vibrOn = sp.getBoolean("vibration",true);
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("text","From TR");
        intent.putExtra("title","From TR");
        intent.putExtra("interval",freq);
        intent.putExtra("notification",notOn);
        intent.putExtra("sound",soundOn);
        intent.putExtra("vibration",vibrOn);
        startService(intent);
    }*/
}
