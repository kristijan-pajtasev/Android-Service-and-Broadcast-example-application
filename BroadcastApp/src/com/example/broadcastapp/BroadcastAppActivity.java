package com.example.broadcastapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BroadcastAppActivity extends Activity
{

    public final static String LOG = "com.example.broadcastapp.BroadcastAppActivity";
    public final static String LOG_RECEIVER = "com.example.broadcastapp.BroadcastAppActivity.Receiver";

    TextView counterTextView;

    private boolean serviceIsRunning = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        counterTextView = (TextView)findViewById(R.id.counterTextView);

    }

    public void startService(View view) {

        if (serviceIsRunning == true) {
            Context context = getApplicationContext();
            Toast t = Toast.makeText(context, "Counter allready running.", Toast.LENGTH_SHORT);
            t.show();
        } else {
            EditText valueEditText = (EditText)findViewById(R.id.valueEditText);

            try {
                int value = (int) Integer.parseInt(valueEditText.getText().toString());
                Intent intent = new Intent(BroadcastAppActivity.this, BackgroundService.class);
                intent.putExtra("message", "Hello Intent Service");
                intent.putExtra("value", value);
                this.startService(intent);
                serviceIsRunning = true;
            } catch (Exception e) {
                Context context = getApplicationContext();
                Toast t = Toast.makeText(context, "Invalid counter value.", Toast.LENGTH_SHORT);
                t.show();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG, "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("my-event"));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG, "onPause");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int secondsLeft = intent.getIntExtra("secondsLeft", 0);

            Log.d(LOG_RECEIVER, "MAIN ACTIVITY");
            Log.d(LOG_RECEIVER, "Seconds left: " + secondsLeft);

            setCounterValue(secondsLeft);
        }
    };

    private void setCounterValue(int secondsLeft) {
        counterTextView.setText(secondsLeft + "");
        if (secondsLeft == 0) {
            serviceIsRunning = false;
        }
    }


}
