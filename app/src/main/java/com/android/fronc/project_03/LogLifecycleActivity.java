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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        makeNotification("onCreate");
        createNotificationChannel();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        makeNotification("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @SuppressWarnings("deprecation")
    @Override
    public Object onRetainNonConfigurationInstance() {
        makeNotification("onRetainNonConfigurationInstance");
        return super.onRetainNonConfigurationInstance();
    }

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

    private void makeNotification(final String methodName) {
        long currentTimeMillis = System.currentTimeMillis();
        printNotification(methodName, currentTimeMillis);
    }

    private void printNotification(final String methodName, long currentTimeMillis) {
        String stringTime = getStringDateTimeFromMillisUnixTime(currentTimeMillis);
        String stringClassName = getClass().getSimpleName();
        Log.d(LOG_TAG, methodName + " " + className + " (" + stringTime + ")");
        if (enableNotifications) {

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

            String[] eventsDetails = new String[2];
            eventsDetails[0] = new String(stringClassName);
            eventsDetails[1] = new String(stringTime);

            inboxStyle.setBigContentTitle(methodName);

            for (int i=0; i < eventsDetails.length; i++) {
                inboxStyle.addLine(eventsDetails[i]);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_lifecycle_notification)
                    .setTicker(getString(R.string.notificationLifecycle))
                    .setContentTitle(methodName + " (" + stringClassName + ")")
                    .setContentText(stringTime)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            int thisNotificationId = (int)currentTimeMillis;
            notificationManager.notify(thisNotificationId, builder.build());
        }
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
