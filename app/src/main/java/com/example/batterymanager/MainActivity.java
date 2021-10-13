package com.example.batterymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.BatteryManager;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    BatteryManager batteryManager;
    TextView batterPercentageTextView = findViewById(R.id.batteryPercentage);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE);
        int batteryPercentage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        batterPercentageTextView.setText(batteryPercentage);
    }
}