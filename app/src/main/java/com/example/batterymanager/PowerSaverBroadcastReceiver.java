package com.example.batterymanager;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.PowerManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PowerSaverBroadcastReceiver extends BroadcastReceiver {
    CardView powerSaverCard;
    @Override
    public void onReceive(Context context, Intent intent) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        powerSaverCard = appCompatActivity.findViewById(R.id.batteryManager_card);
        final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if(pm.isPowerSaveMode()) {
            powerSaverCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            powerSaverCard.setCardBackgroundColor(Color.parseColor("#BCBCBC"));
        }
    }
}