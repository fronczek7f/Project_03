package com.android.fronc.project_03;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class LogLifecycleActivity extends Activity {

    private static final String LOG_TAG = "LogLifecycleActivity";
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "default";

    private NotificationManager notifyManager;
    private boolean enableNotifications = true;
    private final String className;

    public LogLifecycleActivity() {
        super();
        this.className = this.getClass().getName();
    }

    public LogLifecycleActivity(final boolean enableNotifications) {
        this();
        this.enableNotifications = enableNotifications;
    }

    // ==========================================
    // BEGIN Lifecycle method calls we want to be reported.
    // ==========================================

    // Basic lifecycle methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        makeNotification("onCreate");
    }

    @Override
    protected void onStart() {
        makeNotification("onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        makeNotification("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        makeNotification("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        makeNotification("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        makeNotification("onDestroy");
        super.onDestroy();
    }

    // State methods
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        makeNotification("onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        makeNotification("onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    // Configuration methods
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        makeNotification("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    /*
    Warning:
    The onRetainNonConfigurationInstance( ) and getLastNonConfigurationInstance( ) methods
    have been deprecated with Android 3.0 (API level 13).  Youâll note the @SuppressWarnings annotations
    in the code above.  There replacement is considered to be the fragmentâs setRetainInstance(boolean)
    mechanism described for example in
    http://www.intertech.com/Blog/saving-and-retrieving-android-instance-state-part-2/
     */
    @SuppressWarnings("deprecation")
    @Override
    public Object onRetainNonConfigurationInstance() {
        makeNotification("onRetainNonConfigurationInstance");
        return super.onRetainNonConfigurationInstance();
    }

    // Other application state methods
    @Override
    public boolean isFinishing() {
        makeNotification("isFinishing");
        return super.isFinishing();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onLowMemory() {
        Toast.makeText(this, "onLowMemory", Toast.LENGTH_SHORT).show();
        super.onLowMemory();
    }

    // ==========================================
    // END Lifecycle method calls we want to be reported.
    // ==========================================

    /* Alternative methods to convert miliseconds to data and time

    // I method
    String.format("%02d hours %02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(cuttentTimeMillis),
                TimeUnit.MILLISECONDS.toSeconds(cuttentTimeMillis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(cuttentTimeMillis))

    // II method
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(timeStamp);

    int mYear = calendar.get(Calendar.YEAR);
    int mMonth = calendar.get(Calendar.MONTH);
    int mDay = calendar.get(Calendar.DAY_OF_MONTH);

    // III method
    // Date class is deprecated, so use DateFormat class
    DateFormat.getInstance().format(currentTimeMillis);

    DateFormat.getDateInstance().format(new Date(0)));
    DateFormat.getDateTimeInstance().format(new Date(0)));
    DateFormat.getTimeInstance().format(new Date(0)));

    */

    String getStringDateTimeFromMillisUnixTime(long cuttentTimeMillis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(cuttentTimeMillis);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        return String.format("%04d-%02d-%02d %02d:%02d:%02d:%03d",
                year, month, day, hours, minutes, seconds,cuttentTimeMillis%1000);
    }

    // An auxiliary method for notification printing
    // Both methods do the same. It's up to you which one
    // you prefer to use

    private void makeNotification(final String methodName) {
        long currentTimeMillis = System.currentTimeMillis();
        printNotificationCustom(methodName, currentTimeMillis);
    }

    private void printNotification(final String methodName, long currentTimeMillis) {/*
        String stringTime = getStringDateTimeFromMillisUnixTime(currentTimeMillis);
        String stringClassName = getClass().getSimpleName();
        Log.d(LOG_TAG, methodName + " " + className + " (" + stringTime + ")");
        if (enableNotifications) {
            /*
            // Set Notification Title
            String strTitle = getString(R.string.notificationtitle);
            // Set Notification Text
            String strText = getString(R.string.notificationtext);

            // Open NotificationView Class on Notification Click
            Intent intent = new Intent(this, NotificationView.class);
            // Send data to NotificationView Class
            intent.putExtra("title", strtitle);
            intent.putExtra("text", strtext);
            // Open NotificationView.java Activity
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            // Add Big View Specific Configuration
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

            String[] eventsDetails = new String[2]; // max 6 lines
            eventsDetails[0] = new String(stringClassName);
            eventsDetails[1] = new String(stringTime);

            // Sets a title for the Inbox style big view
            inboxStyle.setBigContentTitle(methodName);

            // Moves events into the big view
            for (int i=0; i < eventsDetails.length; i++) {
                inboxStyle.addLine(eventsDetails[i]);
            }

            //Create Notification using NotificationCompat.Builder
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_lifecycle_notification)
                    .setTicker(getString(R.string.notificationLifecycle))
                    .setContentTitle(methodName + " (" + stringClassName + ")")
                    .setContentText(stringTime)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);

            // Create Notification Manager
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Build Notification with Notification Manager
            int thisNotificationId = (int)currentTimeMillis;
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }*/
    }

    private void printNotificationCustom(final String methodName, long currentTimeMillis) {
        createNotificationChannel();
        String stringTime = getStringDateTimeFromMillisUnixTime(currentTimeMillis);
        String stringClassName = getClass().getSimpleName();
        Log.d(LOG_TAG, methodName + " " + className + " (" + stringTime + ")");/*
        if (enableNotifications) {
            /*
            // Set Notification Title
            String strTitle = getString(R.string.notificationtitle);
            // Set Notification Text
            String strText = getString(R.string.notificationtext);

            // Open NotificationView Class on Notification Click
            Intent intent = new Intent(this, NotificationView.class);
            // Send data to NotificationView Class
            intent.putExtra("title", strtitle);
            intent.putExtra("text", strtext);
            // Open NotificationView.java Activity
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);


            // Using RemoteViews to bind custom layouts into Notification
            RemoteViews remoteViews = new RemoteViews(getPackageName(),
                    R.layout.my_notification_layout);

            // Locate and set the Image into my_notification_layout.xml ImageViews
            remoteViews.setImageViewResource(R.id.my_notification_image,R.drawable.ic_lifecycle_notification);

            // Locate and set the Text into my_notification_layout.xml TextViews
            remoteViews.setTextViewText(R.id.my_notification_class_name, stringClassName);
            remoteViews.setTextViewText(R.id.my_notification_method_name, methodName);
            remoteViews.setTextViewText(R.id.my_notification_timestamp, stringTime);

            //Create Notification using NotificationCompat.Builder
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_lifecycle_notification)
                    .setTicker(getString(R.string.notificationLifecycle))
                    .setContent(remoteViews)
                    .setAutoCancel(true);

            // Create Notification Manager
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // Build Notification with Notification Manager
            // In this case we don't care about notification ID, so we use the same ID
            // for all of them.
            int thisNotificationId = (int)currentTimeMillis;
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }*/
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
