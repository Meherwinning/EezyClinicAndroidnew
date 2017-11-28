package com.vempower.eezyclinic;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vempower.eezyclinic.utils.Utils;

/**
 * Created by satish on 28/11/17.
 */

//https://github.com/firebase/quickstart-android/blob/master/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/MyFirebaseMessagingService.java
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final String FCM_MESSAGE_KEY="fcm_message_key";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        //http://stackoverflow.com/questions/37711082/how-to-handle-notification-when-app-in-background-in-firebase

        // Check if message contains a notification payload.
        if (remoteMessage.getData() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().get("mymsg"));
            // sendNotification(remoteMessage.getNotification().getBody());

           // generateNotification(getApplicationContext(), remoteMessage.getData().get("mymsg"));
             generateNotification(getApplicationContext(), remoteMessage.getNotification().getBody());


        }

        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
           // sendNotification(remoteMessage.getNotification().getBody());

            generateNotification(getApplicationContext(), remoteMessage.getNotification().getBody());
        }*/

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.


    }
    // [END receive_message]

    /*{
        "response": {
        "notification_message": "Sathish has accept your game",
                "user_id": "49",
                "oppnent_user_id": "50",
                "game_status" : "4"
        "game_id": "12"
    }
    }*/

    private static void generateNotification(Context context, String message) {

        // Toast.makeText(context,notificationMessage,Toast.LENGTH_LONG).show();
        String title = context.getString(R.string.app_name);
        int icon = R.mipmap.ic_launcher;
        //	long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getActivity(context,
                (int) (System.currentTimeMillis() % 100000),
                new Intent(), PendingIntent.FLAG_UPDATE_CURRENT);
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);*/

        //start

        CharSequence name=Utils.getStringFromResources(R.string.app_name);
        String desc= Utils.getStringFromResources(R.string.default_notification_channel_desc);
        int imp=NotificationManager.IMPORTANCE_HIGH;
        final String ChannelID=Utils.getStringFromResources(R.string.default_notification_channel_id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel mChannel = new NotificationChannel(ChannelID, name,
                    imp);
            mChannel.setDescription(desc);
            mChannel.setLightColor(Color.CYAN);
            mChannel.canShowBadge();
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        //End
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,ChannelID);

        Notification notification = builder
                .setContentIntent(intent)
                .setSmallIcon(icon)
                .setTicker(message)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(
                        new NotificationCompat.BigTextStyle()
                                .bigText(message))
                .setContentText(message).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//		notification.defaults |= Notification.DEFAULT_SOUND;
//		notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(98278734/*(int) (System.currentTimeMillis() % 100000)*/,
                notification);



    }

   /* public void initChannels(Context context,NotificationManager notificationManager) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);
    }
*/

}
