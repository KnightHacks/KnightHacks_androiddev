package org.httpsknighthacks.knighthacksandroid;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.app.Notification.DEFAULT_SOUND;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "KnightHacksIsTheBest";
    public static final String TOKEN_BROADCAST = "GOKnights";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent [] activityIntent = new Intent[3];
        activityIntent[0] = new Intent(this, MyFirebaseMessagingService.class);

        // Actions intents for the notification
        activityIntent[1] = new Intent(this, LiveUpdates.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activityIntent[2] = new Intent(this, Schedule.class);

        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                activityIntent[0], PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent actionIntent = PendingIntent.getActivity(this, 0,
                activityIntent[1], PendingIntent.FLAG_ONE_SHOT);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        super.onMessageReceived(remoteMessage);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "GENERAL")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.rover)
                .addAction(R.mipmap.ic_launcher, "Live Updates", actionIntent)
                .setContentIntent(actionIntent)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setCategory(Notification.CATEGORY_EVENT)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(1, notification.build());

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onNewToken(String token)
    {
        Log.d("myFirebaseId", "Refreshed token: " + token);

        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
    }
}