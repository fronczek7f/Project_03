package com.android.fronc.project_03;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;

public class MainActivity extends LogLifecycleActivity {

    private Chronometer chronometer;
    static LogLifecycleActivity logLifecycleActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        logLifecycleActivity = new LogLifecycleActivity(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        chronometer.start();
    }

    protected void onResume() {
        super.onResume();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public void goToAct(View view) {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

    public void finishApp(View view) {
        finish();
        System.exit(0);
    }
}
