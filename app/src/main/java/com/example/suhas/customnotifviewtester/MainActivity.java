package com.example.suhas.customnotifviewtester;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
    }

    public void sendNotification(View v) {
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ResultActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ResultActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        Log.d("Intent", "sendNotification: "+resultIntent.toString());
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_dialog_alert)
                        .setContentTitle("Protect My Privacy?")
                        .setContentText("Swipe down to respond")
                        .setAutoCancel(true);

        RemoteViews notificationView = getComplexNotificationView();
        notificationView.setOnClickPendingIntent(R.id.yes, resultPendingIntent);
        mBuilder.setCustomBigContentView(notificationView);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    private RemoteViews getComplexNotificationView() {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews notificationView = new RemoteViews(
                getPackageName(),
                R.layout.activity_custom_notification
        );

        // Locate and set the Image into customnotificationtext.xml ImageViews
        notificationView.setImageViewResource(
                R.id.imagenotileft,
                R.drawable.ic_pmp);

        // Locate and set the Text into customnotificationtext.xml TextViews
        notificationView.setTextViewText(R.id.title, "Access Information");

        String msg;
        Spanned msgHtml;
        msg = "<b>" + "Admob" + "</b>" + " being used by:";
        msgHtml = Html.fromHtml(msg);

        notificationView.setTextViewText(R.id.textFirstLine, msgHtml);

        notificationView.setImageViewResource(
                R.id.imageAppIcon1,
                R.drawable.fb_icon);
        notificationView.setTextViewText(R.id.textAppName1, "Facebook");

        /*notificationView.setViewVisibility(R.id.layoutApp2, View.GONE);
        notificationView.setViewVisibility(R.id.layoutApp3, View.GONE);*/

        notificationView.setImageViewResource(
                R.id.imageAppIcon2,
                R.drawable.maps_icon);
        notificationView.setTextViewText(R.id.textAppName2, "Maps");

        notificationView.setImageViewResource(
                R.id.imageAppIcon3,
                R.drawable.ic_whatsapp);
        notificationView.setTextViewText(R.id.textAppName3, "WhatsApp");

        msg = "and " + "10" + " more apps wants to access "
                + "<b>" + "Location" + "</b>."
                + " You previously chose to "
                + "<b><i>" + "Deny " + "</i></b>"
                + "Location" + ". "
                + "Continue to "
                + "<b><i>" + "Deny " + "</i></b> "
                + "<b>Location</b>"
                + " for all future requests from "
                + "AdMob" + "?";
        msgHtml = Html.fromHtml(msg);
        notificationView.setTextViewText(R.id.textBelow, msgHtml);

        return notificationView;
    }
}
