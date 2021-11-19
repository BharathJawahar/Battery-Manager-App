package com.example.batterymanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class WifiBroadcastReceiver extends BroadcastReceiver {
    CardView wifiCard;

    @Override
    public void onReceive(Context context, Intent intent) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        wifiCard = appCompatActivity.findViewById(R.id.wifi_card);
        WifiManager wifiManager = (WifiManager) appCompatActivity.getSystemService(Context.WIFI_SERVICE);
         if (wifiManager.isWifiEnabled())
            wifiCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        else
            wifiCard.setCardBackgroundColor(Color.parseColor("#BCBCBC"));
    }

}
