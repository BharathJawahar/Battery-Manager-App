package com.example.batterymanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    CardView btCard;
    @Override
    public void onReceive(Context context, Intent intent) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        btCard = appCompatActivity.findViewById(R.id.bt_card);
        final int bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
        switch(bluetoothState) {
            case BluetoothAdapter.STATE_ON:
                btCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case BluetoothAdapter.STATE_OFF:
                btCard.setCardBackgroundColor(Color.parseColor("#BCBCBC"));
                break;
        }
    }
}