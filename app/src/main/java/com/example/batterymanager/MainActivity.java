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

    TextView batterPercentageTextView, batteryStatus;

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

                    int batteryPercentage = intent.getIntExtra("level", 0);
                    String batteryPercentageText = batteryPercentage + "%";
                    batterPercentageTextView.setText(batteryPercentageText);

                    if(BatteryManager.BATTERY_STATUS_CHARGING == 2) {
                        if(intent.getIntExtra("level", 0) == 100) {
                            batteryStatus.setText("Charged");
                        }
                        else {
                            batteryStatus.setText("Charging");
                        }
                    }
                    else    batteryStatus.setText("Discharging");

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