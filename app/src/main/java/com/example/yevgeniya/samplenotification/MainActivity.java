package com.example.yevgeniya.samplenotification;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    String text = "MA Text";
    String title = "MA Title";
    TextView textTV;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private String freq;
    private boolean notOn;
    private boolean soundOn;
    private boolean vibrOn;
    private boolean fromMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textTV = (TextView)findViewById(R.id.textTV);
        sp= PreferenceManager.getDefaultSharedPreferences(this);

        //IntentFilter filter=new IntentFilter("sender");
        //Sender sender=new Sender();
        //registerReceiver(sender,filter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        freq = sp.getString("interval","1");
        notOn = sp.getBoolean("notification",true);
        soundOn = sp.getBoolean("sound",true);
        vibrOn = sp.getBoolean("vibration",true);
        fromMain = sp.getBoolean("main",true);
        intent = new Intent(this,MyService.class);
        intent.putExtra("text",text);
        intent.putExtra("title",title);
        intent.putExtra("interval","1");
        intent.putExtra("notification",true);
        intent.putExtra("sound",true);
        intent.putExtra("vibration",true);
        if(sp.getBoolean("main",true)){
            startService(intent);
        }

        textTV.setText("Freq "+freq+" NotOn "+notOn+" soundON "+soundOn+" vibrOn "+vibrOn+" text "+text+" title "+title+" fromMain "+fromMain);
    }


    public void configActivity(View view){
        Intent intent = new Intent(this,ConfigActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void stop(View view){
        Intent intent = new Intent(this,MyService.class);
        stopService(intent);

    }

    public void start(View view){
        intent = new Intent(this,MyService.class);
        intent.putExtra("text",text);
        intent.putExtra("title",title);
        intent.putExtra("interval","1");
        intent.putExtra("notification",true);
        intent.putExtra("sound",true);
        intent.putExtra("vibration",true);
        startService(intent);

    }

}
