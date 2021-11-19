package com.example.batterymanager;

import static java.lang.Thread.sleep;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;

public class MainActivity extends AppCompatActivity {


    TextView powerThreshold, smartThreshold;

    BatteryBroadcastReceiver batteryBroadcastReceiver;
    WifiBroadcastReceiver wifiBroadcastReceiver;
    InternetBroadcastReceiver internetBroadcastReceiver;
    BluetoothBroadcastReceiver bluetoothBroadcastReceiver;
    PowerSaverBroadcastReceiver powerSaverBroadcastReceiver;
    IntentFilter batteryIntentFilter, wifiIntentFilter, internetIntentFilter, bluetoothIntentFilter, powerSaverIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        powerThreshold = findViewById(R.id.powerThreshold);
        smartThreshold = findViewById(R.id.smartThreshold);
        SeekBar seekBar = (SeekBar) findViewById(R.id.thresholdSeekBar);
        SeekBar smartSeekBar = (SeekBar) findViewById(R.id.smartSeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                powerThreshold.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        smartSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                smartThreshold.setText(String.valueOf(progress) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Switch powerSwitch = findViewById(R.id.isPower);
        if(powerSwitch.isChecked()) {
            powerThreshold.setVisibility(View.VISIBLE);
            seekBar.setVisibility(View.VISIBLE);
        }
        else {
            powerThreshold.setVisibility(View.INVISIBLE);
            seekBar.setVisibility(View.INVISIBLE);
        }

        powerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    powerThreshold.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                }
                else {
                    powerThreshold.setVisibility(View.INVISIBLE);
                    seekBar.setVisibility(View.INVISIBLE);
                }
            }
        });



        Switch smartSwitch = findViewById(R.id.smartSwitch);
        if(smartSwitch.isChecked()) {
            smartThreshold.setVisibility(View.VISIBLE);

            smartSeekBar.setVisibility(View.VISIBLE);
        }
        else {
            smartThreshold.setVisibility(View.INVISIBLE);
            smartSeekBar.setVisibility(View.INVISIBLE);
        }

        smartSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    smartThreshold.setVisibility(View.VISIBLE);
                    smartSeekBar.setVisibility(View.VISIBLE);
                }
                else {
                    smartThreshold.setVisibility(View.INVISIBLE);
                    smartSeekBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        batteryBroadcastReceiver = new BatteryBroadcastReceiver();
        batteryIntentFilter = new IntentFilter();
        batteryIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.registerReceiver(batteryBroadcastReceiver, batteryIntentFilter);

        wifiBroadcastReceiver = new WifiBroadcastReceiver();
        wifiIntentFilter = new IntentFilter();
        wifiIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        this.registerReceiver(wifiBroadcastReceiver, wifiIntentFilter);
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        CardView wifiCard = findViewById(R.id.wifi_card);
        if (wifiManager.isWifiEnabled())
            wifiCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        else
            wifiCard.setCardBackgroundColor(Color.parseColor("#BCBCBC"));


        internetBroadcastReceiver = new InternetBroadcastReceiver();
        internetIntentFilter = new IntentFilter();
        internetIntentFilter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        this.registerReceiver(internetBroadcastReceiver, internetIntentFilter);

        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        bluetoothIntentFilter = new IntentFilter();
        bluetoothIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(bluetoothBroadcastReceiver, bluetoothIntentFilter);
        CardView btCard = findViewById(R.id.bt_card);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled())
            btCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        else
            btCard.setCardBackgroundColor(Color.parseColor("#BCBCBC"));

        powerSaverBroadcastReceiver = new PowerSaverBroadcastReceiver();
        powerSaverIntentFilter = new IntentFilter();
        powerSaverIntentFilter.addAction(PowerManager.ACTION_POWER_SAVE_MODE_CHANGED);
        this.registerReceiver(powerSaverBroadcastReceiver, powerSaverIntentFilter);

    }

    public void wifi(View view) {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()) wifiManager.setWifiEnabled(false);
        else wifiManager.setWifiEnabled(true);
    }

    public void internet(View view) {
        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
    }

    public void bluetooth(View view) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter.isEnabled())    bluetoothAdapter.disable();
        else bluetoothAdapter.enable();
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void power(View view) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean bCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        if(!bCharging) {
            final PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
            Duration time = pm.getBatteryDischargePrediction();
            Toast.makeText(this, "Battery will last for " + String.valueOf(time.toMinutes()) + "minutes", Toast.LENGTH_LONG).show();
            int temp = pm.getCurrentThermalStatus();
            Toast.makeText(this, "Battery is at " + String.valueOf(temp) + "degree", Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(this, "Battery is Charging", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(batteryBroadcastReceiver, batteryIntentFilter);
        registerReceiver(wifiBroadcastReceiver, wifiIntentFilter);
        registerReceiver(internetBroadcastReceiver, internetIntentFilter);
        registerReceiver(bluetoothBroadcastReceiver, bluetoothIntentFilter);
        registerReceiver(powerSaverBroadcastReceiver, powerSaverIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryBroadcastReceiver);
        unregisterReceiver(wifiBroadcastReceiver);
        unregisterReceiver(internetBroadcastReceiver);
        unregisterReceiver(bluetoothBroadcastReceiver);
        unregisterReceiver(powerSaverBroadcastReceiver);
    }
}