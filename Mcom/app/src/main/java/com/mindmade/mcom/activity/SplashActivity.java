package com.mindmade.mcom.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mindmade.mcom.R;

public class SplashActivity extends AppCompatActivity {
    private int interval = 10 * 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        callNextIntent(TabActivity.class);
    }

    public void callNextIntent(final Class intentclass) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextIntent = new Intent(SplashActivity.this, intentclass);
                startActivity(nextIntent);
                finish();
            }
        }, interval);
    }
}
