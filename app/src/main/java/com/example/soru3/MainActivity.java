package com.example.soru3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnnot;
    Button btnstart;
    Button btnstop;
    private String CHANNEL_ID="MYAPP123";
    NotificationManager mNotifyManager;
    NotificationChannel notificationChannel;
    TextView editTextTime;
    TextView editTextNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnnot=findViewById(R.id.btnNot);
        btnstart=findViewById(R.id.btnstart);
        btnstop=findViewById(R.id.btnstop);
        IntentFilter filter=new IntentFilter();
        editTextTime=findViewById(R.id.editTextTime);
        editTextNote=findViewById(R.id.editTextNote);
        MyReceiver mr= new MyReceiver();
        this.registerReceiver(mr,filter);
        initUI();

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =  new NotificationChannel(CHANNEL_ID, "Omer Notification",NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Omer");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
        mNotifyManager.cancelAll();
    }
    private void initUI() {
        btnnot.setOnClickListener(v -> notClicked());
        btnstart.setOnClickListener(v -> startServiceClicked());
        btnstop.setOnClickListener(v -> stopServiceClicked());


    }
    public void notClicked(){
        Intent notificationIntent =
                new Intent(this, MainActivity.class);

        SystemClock.sleep(Integer.parseInt(editTextTime.getText().toString()));
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(
                this,1,   notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.citations);

        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(this, CHANNEL_ID)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.ic_action_name)
                .setContentTitle("You've been notified!")
                .setContentText(editTextNote.getText().toString())
                .addAction(R.drawable.ic_action_name, "Ok", notificationPendingIntent)
                .addAction(R.drawable.ic_action_name, "Cancel", notificationPendingIntent)
                .setContentIntent(notificationPendingIntent);


        mNotifyManager.notify(1, mBuilder.build());

    }
    public void startServiceClicked(){
        Intent myintent=new Intent(this, MyService.class);
        myintent.putExtra("Note",editTextNote.getText().toString());
        myintent.putExtra("Time",Integer.parseInt(editTextTime.getText().toString()));
        startService(myintent);

    }

    public void stopServiceClicked(){
        stopService(new Intent(this, MyService.class));

    }
}