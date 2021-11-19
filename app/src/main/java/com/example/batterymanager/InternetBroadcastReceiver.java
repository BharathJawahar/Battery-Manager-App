package com.example.batterymanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class InternetBroadcastReceiver extends BroadcastReceiver {
    CardView internetCard;

    @Override
    public void onReceive(Context context, Intent intent) {
        AppCompatActivity appCompatActivity = (AppCompatActivity) context;
        internetCard = appCompatActivity.findViewById(R.id.internet_card);
        TelephonyManager telephonyService = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Method getMobileDataEnabledMethod = null;
        try {
            getMobileDataEnabledMethod = Objects.requireNonNull(telephonyService).getClass().getDeclaredMethod("getDataEnabled");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            if((boolean) (Boolean) getMobileDataEnabledMethod.invoke(telephonyService))
                internetCard.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            else
                internetCard.setCardBackgroundColor(Color.parseColor("#BCBCBC"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
