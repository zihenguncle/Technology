package com.example.user;

import android.content.Context;

public class UserGetContent {
    public static Context context;

    public static void getContent(Context appContent){
        context = appContent;
    }

    public static Context getContent(){
        return context;
    }

}
