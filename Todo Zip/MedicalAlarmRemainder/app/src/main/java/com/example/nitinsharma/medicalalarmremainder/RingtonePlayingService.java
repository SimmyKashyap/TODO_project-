package com.example.nitinsharma.medicalalarmremainder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by nitin sharma on 18-Dec-18.
 */

public class RingtonePlayingService extends Service {

    Ringtone ringtone;
    private static final String URI_BASE = RingtonePlayingService.class.getName() + ".";
    public static final String ACTION_DISMISS = URI_BASE + "ACTION_DISMISS";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {

            return START_REDELIVER_INTENT;
        }

        String action = intent.getAction();

        if (RingtonePlayingService.ACTION_DISMISS.equals(action))
            dismissRingtone();
        else {
            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (alarmUri == null) {
                Log.v("tone","notification");
                alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
            ringtone = RingtoneManager.getRingtone(this, alarmUri);
            ringtone.play();
        }

        return START_NOT_STICKY;

    }public void dismissRingtone() {
        // stop the alarm rigntone
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);

        // also dismiss the alarm to ring again or trigger again
        AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        aManager.cancel(pendingIntent);

        // Canceling the current notification
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.cancel(321);
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
    }

}
