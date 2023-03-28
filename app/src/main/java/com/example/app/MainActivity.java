package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.cert.CertPathBuilder;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private EditText edTime;
    private Button btSet, btStop;
    private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 10;
    ArrayList<String> data;
    int i = 0;
    Timer timer = new Timer();
    private CertPathBuilder builder;
    private int notificationId;

    @SuppressLint({"MissingInflatedId", "NotificationPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSet = findViewById(R.id.btSet);
        btStop = findViewById(R.id.btStop);
        edTime = findViewById(R.id.edTime);

        MyDBHelper dbHelper = new MyDBHelper(this);
        dbHelper.adddata("jay swaminarayan");
        dbHelper.adddata("hari");
        dbHelper.adddata("krupalu");
        dbHelper.adddata("valam");
        dbHelper.adddata("dayalu");

        data = dbHelper.fetchdata();

        btSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setstarttime(Integer.parseInt(edTime.getText().toString()) * 1000L);
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMessageDisplay();
            }
        });

    }

    private void setstarttime(long milisecond) {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createNotificationChannel(data.get(i));
                        i++;

                        if (i == data.size()) {

                            i = 0;
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, milisecond);
    }

    private void createNotificationChannel(String namemodel) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.maharaj, null);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap smallIcon = bitmapDrawable.getBitmap();
        Notification notification;

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.maharaj)
                    .setContentTitle(namemodel)
                    .setSubText("Smaran App")
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID)
                    .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound))
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_HIGH));
        } else {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.maharaj)
                    .setContentTitle(namemodel)
                    .setSubText("Smaran App")
                    .setAutoCancel(true)
                    .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sound))
                    .build();

            nm.notify(NOTIFICATION_ID, notification);
        }

    }

    private void stopMessageDisplay() {
        timer.cancel();
    }

}