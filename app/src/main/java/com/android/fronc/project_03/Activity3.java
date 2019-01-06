package com.android.fronc.project_03;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class Activity3 extends LogLifecycleActivity {

    private TextView mTextView;
    private int mCount;
    private Date startTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        mTextView = (TextView) findViewById(R.id.textView);
}

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mTextView.setText(savedInstanceState.getInt("count"));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("count", mCount);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mCount++;
        mTextView.setText(String.valueOf(mCount));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCount++;
        mTextView.setText(String.valueOf(mCount));
    }
}
