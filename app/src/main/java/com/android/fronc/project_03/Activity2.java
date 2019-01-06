package com.android.fronc.project_03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity2 extends LogLifecycleActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
    }

    public void goToActNext(View view) {
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }
}
