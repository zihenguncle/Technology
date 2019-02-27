package com.example.component.mvp.retrofit;

public class RetrofitUtils {


    public static RetrofitUtils instance;
    //懒汉式单例
    public static RetrofitUtils getInstance(){
        if(instance == null){
            synchronized (RetrofitUtils.class){
                instance = new RetrofitUtils();
            }
        }
        return instance;
    }

    private RetrofitUtils(){

    }

}
