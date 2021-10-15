package com.example.batterymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.BatteryManager;
import android.os.Bundle;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BatteryManager batteryManager;
    TextView batterPercentageTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batterPercentageTextView = findViewById(R.id.batteryPercentage);
        batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
        int batteryPercentage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        String batteryPercentageText = batteryPercentage + "%";
        batterPercentageTextView.setText(batteryPercentageText);
    }
}