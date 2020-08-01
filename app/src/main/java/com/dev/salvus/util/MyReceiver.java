package com.dev.salvus.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dev.salvus.activities.HomeActivity;

public class MyReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    private int count=0;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.e("LOB","onReceive");
        //final Handler handler = new Handler();
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            // do whatever you need to do here
            //Toast.makeText(context, "SCREENOFF = "+count, Toast.LENGTH_SHORT).show();
            wasScreenOn = false;
            ++count;
            Log.e("LOB","wasScreenOn"+wasScreenOn);

//        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
//
//            // and do whatever you need to do here
//            Toast.makeText(context, "SCREENON", Toast.LENGTH_SHORT).show();
//            wasScreenOn = true;
//            ++count;

        } else if(count==3){

            Log.e("LOB", "userpresent");
            Log.e("LOB", "wasScreenOn" + wasScreenOn);
            Intent i = new Intent(context, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            count=0;
        }
        else if(count>4)
        {
            count=0;
        }

        final Runnable r = new Runnable() {
            public void run() {
                //
                //Toast.makeText(context, "New Thread = "+count, Toast.LENGTH_SHORT).show();

            }
        };

        //handler.postDelayed(r, 5000);

    }
}