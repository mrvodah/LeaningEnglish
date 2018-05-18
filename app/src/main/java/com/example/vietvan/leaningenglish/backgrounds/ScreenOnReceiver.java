package com.example.vietvan.leaningenglish.backgrounds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.vietvan.leaningenglish.activities.ReviewActivity;

/**
 * Created by VietVan on 01/04/2018.
 */

public class ScreenOnReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, ReviewActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
