package com.example.orderfoodonline.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.orderfoodonline.R;
import com.example.orderfoodonline.activities.HomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessing extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if (message.getNotification() != null)
        {
            //hiển thông báo lên
            showNoti(message.getNotification().getTitle(), message.getNotification().getBody());
        }
    }

    private void showNoti(String title, String body) {
        //intent gọi đến màn hình chính
        Intent intent = new Intent(this, HomeActivity.class);
        //add flags hiện lên trên đầu
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String channelId = "notification";

        PendingIntent   pendingIntent = PendingIntent.getActivity(this, 0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder  builder = new NotificationCompat.Builder(getApplicationContext(),channelId)
                .setSmallIcon(R.drawable.splashlogo)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000,1000,2000,2000})//set thời gian giây
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        builder = builder.setContent(customView(title, body));

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "foodonline", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());
    }

    private RemoteViews customView(String title, String body){
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.tvtitleTB, title);
        remoteViews.setTextViewText(R.id.tvnoti, body);
        remoteViews.setImageViewResource(R.id.imgnoti, R.drawable.splashlogo);
        return remoteViews;
    }
}
