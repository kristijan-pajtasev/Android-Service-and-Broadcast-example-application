package com.example.broadcastapp;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by kristijan on 3/3/14.
 */
public class BackgroundService extends IntentService {

    public final static String LOG = "com.example.broadcastapp.BackgroundService";

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG, "onHandleIntent action started");

        String message = intent.getStringExtra("message");
        int value = intent.getIntExtra("value", 0);
        Log.d(LOG, "Intent extra message: " + message);
        Log.d(LOG, "Intent extra value: " + value);

        Intent localIntent = new Intent("my-event");

        int secondsLeft = 0;

        for (int i = value; i > 0; i--) {
            localIntent.putExtra("secondsLeft", i);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            SystemClock.sleep(1000);
        }

        //localIntent.putExtra("message", "Service Message");

        localIntent.putExtra("secondsLeft", 0);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);



    }
}
