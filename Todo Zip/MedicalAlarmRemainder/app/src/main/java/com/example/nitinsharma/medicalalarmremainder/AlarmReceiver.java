package com.example.nitinsharma.medicalalarmremainder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

/**
 * Created by nitin sharma on 29-Nov-18.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {
    Notification.Builder builder;
    NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Recieved", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, RingtonePlayingService.class);
        context.startService(i);


      /*  Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri alarmNotificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
*/
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Intent for main activity i.e when user click on the notification
        Intent j = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        j,
                        PendingIntent.FLAG_ONE_SHOT
                );
        //Intent for dismiss button on notification
        Intent dismissIntent = new Intent(context, RingtonePlayingService.class);
        dismissIntent.setAction(RingtonePlayingService.ACTION_DISMISS);

        PendingIntent pendingIntent1 = PendingIntent.getService(context,
                123, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action
                (android.R.drawable.ic_lock_idle_alarm, "DISMISS AlARM", pendingIntent1);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))

                .setContentTitle("Remainder")
                .setContentText("Tap to Disable Remainder....")
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL)
                .setVibrate(new long[]{1000, 1000})
                .addAction(action)
                .setContentIntent(pendingIntent);

        manager.notify(0, mBuilder.build());
       /* Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM

        );
        try{
            MediaPlayer mp = MediaPlayer.create(context,uri);
            mp.start();

        } catch(Exception e){}
*/
    }
}
