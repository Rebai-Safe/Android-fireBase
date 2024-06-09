package com.example.projectif4firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;

public class PriseReminder extends BroadcastReceiver {

    //will be executed whenever our alarm is fired
    @Override
    public void onReceive(Context context, Intent intent) {
       int h=intent.getIntExtra("HeurePrise",0);

        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "Prise Medicament ");
        i.putExtra(AlarmClock.EXTRA_HOUR, h);
        i.putExtra(AlarmClock.EXTRA_MINUTES, 00);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}
