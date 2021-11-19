package com.example.batterymanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.BatteryState;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BatteryBroadcastReceiver extends BroadcastReceiver {
    TextView batteryPercentage, batteryStatus;

    @Override
    public void onReceive(Context context, Intent intent) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            batteryPercentage = appCompatActivity.findViewById(R.id.batteryPercentage);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryLevel=(int)(((float)level / (float)scale) * 100.0f);
            batteryPercentage.setText(Integer.toString(batteryLevel));

            batteryStatus = appCompatActivity.findViewById(R.id.batteryStatus);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            if(status == BatteryManager.BATTERY_STATUS_CHARGING) {
                batteryStatus.setText("Charging");

                SeekBar seekBar = (SeekBar) appCompatActivity.findViewById(R.id.thresholdSeekBar);
                if(batteryLevel==100) {
                    NotificationChannel notificationChannel = new NotificationChannel("channel1", "Channel 1", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setDescription("This is Channel ");
                    NotificationManager notificationManager = appCompatActivity.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(notificationChannel);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    Notification notification = new NotificationCompat.Builder(context, "channel1").setSmallIcon(R.drawable.ic_battery).setContentTitle("Battery Charged").setContentText("Battery has reached its maximum capacity").setPriority(NotificationCompat.PRIORITY_HIGH).setCategory(NotificationCompat.CATEGORY_ALARM).build();
                    notificationManagerCompat.notify(1, notification);

                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.raw);
                    mediaPlayer.start();
                    Toast.makeText(context, "Battery Charged", Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(context)
                            .setTitle("Battery Charged")
                            .setMessage("Battery has reached its maximum capacity")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mediaPlayer.stop();
                                }
                            }).show();
                }

                Switch isPower = appCompatActivity.findViewById(R.id.isPower);
                if(batteryLevel==seekBar.getProgress() && isPower.isChecked()) {
                    NotificationChannel notificationChannel = new NotificationChannel("channel1", "Channel 1", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setDescription("This is Channel");
                    NotificationManager notificationManager = appCompatActivity.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(notificationChannel);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    Notification notification = new NotificationCompat.Builder(context, "channel1").setSmallIcon(R.drawable.ic_battery).setContentTitle("Battery reached Power Threshold").setContentText("Battery has reached to " + String.valueOf(seekBar.getProgress())).setPriority(NotificationCompat.PRIORITY_HIGH).setCategory(NotificationCompat.CATEGORY_ALARM).build();
                    notificationManagerCompat.notify(1, notification);

                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.raw);
                    mediaPlayer.start();
                    Toast.makeText(context, "Battery Charged", Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(context)
                            .setTitle("Battery reached Power Threshold")
                            .setMessage("Battery has reached to " + String.valueOf(seekBar.getProgress()))
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mediaPlayer.stop();
                                }
                            }).show();
                }

            }            else if(status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                Switch smartSwitch = appCompatActivity.findViewById(R.id.smartSwitch);
                SeekBar smartSeekBar = (SeekBar) appCompatActivity.findViewById(R.id.smartSeekBar);

                if((batteryLevel==smartSeekBar.getProgress() && smartSwitch.isChecked()) && (BatteryState.STATUS_CHARGING == 2)) {
                    NotificationChannel notificationChannel = new NotificationChannel("channel1", "Channel 1", NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setDescription("This is Channel");
                    NotificationManager notificationManager = appCompatActivity.getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(notificationChannel);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                    Notification notification = new NotificationCompat.Builder(context, "channel1").setSmallIcon(R.drawable.ic_battery).setContentTitle("Battery reached Smart Power Threshold").setContentText("Battery has reached to " + String.valueOf(smartSeekBar.getProgress())).setPriority(NotificationCompat.PRIORITY_HIGH).setCategory(NotificationCompat.CATEGORY_ALARM).build();
                    notificationManagerCompat.notify(1, notification);

                    smartSwitch.setChecked(false);

                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.raw);
                    mediaPlayer.start();
                    Toast.makeText(context, "Battery reached Minimum Battery Threshold", Toast.LENGTH_LONG).show();
                    new AlertDialog.Builder(context)
                            .setTitle("Battery reached Smart Power Threshold")
                            .setMessage("Battery has reached to " + String.valueOf(smartSeekBar.getProgress()) + "\nSmart Activity will turn off Wifi, Bluetooth, and Hotspot")
                            .setCancelable(true)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mediaPlayer.stop();
                                    WifiManager wifiManager = (WifiManager) appCompatActivity.getSystemService(Context.WIFI_SERVICE);
                                    wifiManager.setWifiEnabled(false);
                                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                                    bluetoothAdapter.disable();

                                }
                            }).show();
                    batteryStatus.setText("Discharging");
                }
            }            else if(status == BatteryManager.BATTERY_STATUS_FULL)
                batteryStatus.setText("Charged");


        }
    }
}