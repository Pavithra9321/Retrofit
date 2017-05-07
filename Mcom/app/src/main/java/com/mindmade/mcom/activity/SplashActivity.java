package com.mindmade.mcom.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.mindmade.mcom.R;

public class SplashActivity extends AppCompatActivity {
    private int interval = 10 * 300;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        callNextIntent(TabActivity.class);
        img= (ImageView) findViewById(R.id.splash_imageView);
        try {
            Log.d("suc", "img" + img);
        }
        catch (Exception e){
            Log.d("suc","ex"+e);
        }
    }


    private void callNextIntent(final Class intentClass) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent nextIntent = new Intent(SplashActivity.this, intentClass);
                startActivity(nextIntent);
                finish();
            }
        }, interval);
    }
}
