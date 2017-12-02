package com.example.yevgeniya.samplenotification;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfigActivity extends Activity implements View.OnClickListener {

    Intent intent;
    SharedPreferences sp;
    Button save;
    TextView config;
    private String freq;
    private boolean notOn;
    private boolean soundOn;
    private boolean vibrOn;
    private boolean isServ;
    String text = "CA Text";
    String title = "CA Title";
    SharedPreferences.Editor editor;
    private String spText;
    private String spTitle;
    private boolean fromMain=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        getFragmentManager().beginTransaction().add(R.id.mainLL,new Config()).commit();
        save = (Button)findViewById(R.id.saveBTN);
        save.setOnClickListener(this);
        config = (TextView)findViewById(R.id.configTV);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        freq = sp.getString("interval","");
        notOn = sp.getBoolean("notification",true);
        soundOn = sp.getBoolean("sound",true);
        vibrOn = sp.getBoolean("vibration",true);
        fromMain=sp.getBoolean("main",true);
        isServ  = isMyServiceRunning(MyService.class);
       //spText = sp.getString("caText","");
       // spTitle = sp.getString("caTitle","");
        config.setText("Freq "+freq+" NotOn "+notOn+" soundON "+soundOn+" vibrOn "+vibrOn+" text "+text+" title "+title+" fromMain "+fromMain+" serv "+isServ);
    }

    @Override
    public void onClick(View v) {
        freq = sp.getString("interval","");
        notOn = sp.getBoolean("notification",true);
        soundOn = sp.getBoolean("sound",true);
        vibrOn = sp.getBoolean("vibration",true);
        isServ = isMyServiceRunning(MyService.class);
        //spText = sp.getString("caText","");
        //spTitle = sp.getString("caTitle","");

        editor = sp.edit();
        editor.putBoolean("main",false);
        editor.commit();
        fromMain=sp.getBoolean("main",true);
        config.setText("Freq "+freq+" NotOn "+notOn+" soundON "+soundOn+" vibrOn "+vibrOn+" text "+text+" title "+title+" fromMain "+fromMain+" serv "+isServ);
        intent = new Intent(this,MyService.class);
        intent.putExtra("text",text);
        intent.putExtra("title",title);
        intent.putExtra("interval",freq);
        intent.putExtra("notification",notOn);
        intent.putExtra("sound",soundOn);
        intent.putExtra("vibration",vibrOn);
        startService(intent);
    }

    public void mainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
