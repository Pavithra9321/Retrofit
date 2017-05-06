package com.mindmade.mcom.utilclasses;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.mindmade.mcom.utilclasses.push_notification.InstanceIDListenerService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public class AppController extends Application {

    //objects
    // private RequestQueue mRequestQueue;
    private static AppController mInstance;

   /* private ImageLoader mImageLoader;
    LruBitmapCache mLruBitmapCache;*/

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Intent tokenGetIntent = new Intent(this, InstanceIDListenerService.class);
        startService(tokenGetIntent);

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
    public <S> S createService(Class<S> serviceClass) {

        OkHttpClient httpClient=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();




        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.LIVE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();



        return retrofit.create(serviceClass);

    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
