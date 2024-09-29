package com.example.messmagic.messaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.messmagic.MainActivity;
import com.example.messmagic.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {

    private  final  String CHANNEL_ID ="Channel_ID";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        Intent intent = new Intent(this , MainActivity.class);

        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId=new Random().nextInt();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createNotificationChannel(manager);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent intent1=PendingIntent.getActivities(this ,0 ,new Intent[]{intent}, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        Notification notification ;

            notification = new NotificationCompat.Builder(this , CHANNEL_ID)
                    .setContentTitle(message.getData().get("title"))
                    .setContentText(message.getData().get("message"))
                    .setSmallIcon(R.drawable.user)
                    .setAutoCancel(true)
                    .setContentIntent(intent1)
                    .build();

        manager.notify(notificationId,notification);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager manager) {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID , "ChannelName", NotificationManager.IMPORTANCE_HIGH);

       channel.setDescription("My Desciption");
       channel.enableLights(true);
       channel.setLightColor(Color.WHITE);

       manager.createNotificationChannel(channel);
    }
}
