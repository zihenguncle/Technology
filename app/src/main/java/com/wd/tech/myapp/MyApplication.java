package com.wd.tech.myapp;

import android.app.Application;
import android.content.Context;

import com.example.component.GetContent;

public class MyApplication extends Application {

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context.getApplicationContext();
        GetContent.getContent(context);
    }

}
