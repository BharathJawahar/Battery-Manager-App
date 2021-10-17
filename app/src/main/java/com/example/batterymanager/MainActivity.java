package com.example.batterymanager;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView batterPercentageTextView, batteryVoltageTextView, batteryStatus;
    BroadcastReceiver batteryBroadcastReceiver;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBatteryDetails();
    }

    public void setBatteryDetails() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    batterPercentageTextView = findViewById(R.id.batteryPercentage);
                    batteryStatus = findViewById(R.id.batteryStatus);
                    batteryVoltageTextView = findViewById(R.id.batteryVoltage);

                    int batteryPercentage = intent.getIntExtra("level", 0);
                    double voltage = intent.getIntExtra("voltage", 0) * 0.001;
                    int batteryHealthValue = intent.getIntExtra("health", 0);
                    String batteryType = intent.getStringExtra("technology");
                    String batteryHealth = "Good";
                    switch (batteryHealthValue) {
                        case BatteryManager.BATTERY_HEALTH_GOOD:
                            batteryHealth = "Good";
                            batteryStatus.setBackgroundResource(R.drawable.battery_percentage_background_green);
                            break;
                        case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                            batteryHealth = "Failure";
                            batteryStatus.setBackgroundResource(R.drawable.battery_percentage_background_red);
                            break;
                        case BatteryManager.BATTERY_HEALTH_DEAD:
                            batteryHealth = "Dead";
                            batteryStatus.setBackgroundResource(R.drawable.battery_percentage_background_red);
                            break;
                        case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                            batteryHealth = "Over Voltage";
                            batteryStatus.setBackgroundResource(R.drawable.battery_percentage_background_orange);
                            break;
                        case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                            batteryHealth = "Over Heated";
                            batteryStatus.setBackgroundResource(R.drawable.battery_percentage_background_orange);
                            break;
                        case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                            batteryHealth = "Unknown";
                            batteryStatus.setBackgroundResource(R.drawable.battery_percentage_background_orange);
                            break;
                    }
                    String batteryPercentageText = batteryPercentage + "%";

                    batterPercentageTextView.setText(batteryPercentageText);
                        if(batteryPercentage>80)             batterPercentageTextView.setBackgroundResource(R.drawable.battery_percentage_background_green);
                        else if(batteryPercentage>50)        batterPercentageTextView.setBackgroundResource(R.drawable.battery_percentage_background_blue);
                        else if(batteryPercentage>25)        batterPercentageTextView.setBackgroundResource(R.drawable.battery_percentage_background_orange);
                        else                                 batterPercentageTextView.setBackgroundResource(R.drawable.battery_percentage_background_red);
                    batteryStatus.setText(batteryHealth);
                    batteryVoltageTextView.setText(String.valueOf(voltage) + " v");

                    System.out.println(BatteryManager.BATTERY_STATUS_CHARGING);

                    if(BatteryManager.BATTERY_STATUS_CHARGING == 2 && batteryPercentage == 100) {
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), alarmSound);
                        mp.start();
                    }
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(batteryBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryBroadcastReceiver);
    }
}