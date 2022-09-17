package com.example.soru3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class MyService  extends Service {

    private String CHANNEL_ID="MYAPP123";
    NotificationManager mNotifyManager;
    NotificationChannel notificationChannel;



    @Override
    public void onCreate() {

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "Omer Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Omer");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
        mNotifyManager.cancelAll();

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();


        SystemClock.sleep(intent.getIntExtra("Time",0));


        Intent notificationIntent =      new Intent(this, MainActivity.class);


        PendingIntent notificationPendingIntent = PendingIntent.getActivity(
                this,1,   notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.citations);
        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(this, CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.ic_action_name)
                .setContentTitle("You've been notified!")
                .setContentText(intent.getStringExtra("Note"))
                .addAction(R.drawable.ic_action_name, "Ok", notificationPendingIntent)
                .addAction(R.drawable.ic_action_name, "Cancel", notificationPendingIntent)
                .setContentIntent(notificationPendingIntent);
        mNotifyManager.notify(1, mBuilder.build());


        //    SystemClock.sleep(10000);

        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();

    }



}
