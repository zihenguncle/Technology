package com.example.component.base.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.component.mvp.persenter.IPersenterImpl;
import com.example.component.mvp.view.IView;
import com.example.component.utils.LoadingUtils;
import com.example.component.utils.NetWorkUtils;
import com.example.component.utils.ToastUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract class BaseNetworkActivity extends AppCompatActivity implements IView {

    /**
     * 整个Activity视图的根视图
     */
    View decorView;

    /**
     * 手指按下时的坐标
     */
    float downX, downY;

    /**
     * 手机屏幕的宽度和高度
     */
    float screenWidth, screenHeight;

    private Dialog loadingDialog;

    IPersenterImpl iPersenter;

    public boolean isNeedFCancle = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(intiLayout());
        //初始化控件
        initView();

        //拿到整个activity的视图
        decorView = getWindow().getDecorView();

        // 获得手机屏幕的宽度和高度，单位像素
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        //实例化
        iPersenter = new IPersenterImpl(this);

        //设置数据
        initData();
    }
    //设置布局
    public abstract int intiLayout();

    //初始化布局
    public abstract void initView();

    //设置数据
    public abstract void initData();

    public abstract void success(Object data);

    public abstract void failed(String error);

    //是否要支持滑动关闭
    public void isSlide(Boolean bool){
        isNeedFCancle = bool;
    }


    //GET
    public void startRequestDataGet(String url,Class clazz){
        if(!NetWorkUtils.hasNetwork(this)){
            ToastUtils.toast("当前网络不可用");
            NetWorkUtils.setNetworkMethod(this);
            return;
        } else {
            if (loadingDialog == null) {
                loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
            }
            iPersenter.startRequestGet(url, clazz);
        }
    }


    //DELETE
    public void startRequestDataDelete(String url,Class clazz){
        if(!NetWorkUtils.hasNetwork(this)){
            ToastUtils.toast("当前网络不可用");
            NetWorkUtils.setNetworkMethod(this);
            return;
        } else {
            if (loadingDialog == null) {
                loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
            }
            iPersenter.startRequestDelete(url, clazz);
        }
    }


    //POST
    public void startRequestDataPost(String url, Map<String,String> map, Class clazz){
        if(!NetWorkUtils.hasNetwork(this)){
            ToastUtils.toast("当前网络不可用");
            NetWorkUtils.setNetworkMethod(this);
            return;
        } else {
            if (loadingDialog == null) {
                loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
            }
            iPersenter.startRequestPost(url,map,clazz);
        }
    }


    //PUT
    public void startRequestDataPut(String url, Map<String,String> map, Class clazz){
        if(!NetWorkUtils.hasNetwork(this)){
            ToastUtils.toast("当前网络不可用");
            NetWorkUtils.setNetworkMethod(this);
            return;
        } else {
            if (loadingDialog == null) {
                loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
            }
            iPersenter.startRequestPut(url,map,clazz);
        }
    }


    //FILE
    public void startRequestFile(String url,Map<String,String> map,Class clazz){
        if(!NetWorkUtils.hasNetwork(this)){
            ToastUtils.toast("当前网络不可用");
            NetWorkUtils.setNetworkMethod(this);
            return;
        } else {
            if (loadingDialog == null) {
                loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
            }
            iPersenter.startRequestFile(url,map,clazz);
        }
    }


    //MOREFILE
    public void startRequestMoreFile(String url, Map<String,String> map, List<File> list,Class clazz){
        if(!NetWorkUtils.hasNetwork(this)){
            ToastUtils.toast("当前网络不可用");
            NetWorkUtils.setNetworkMethod(this);
            return;
        } else {
            if (loadingDialog == null) {
                loadingDialog = LoadingUtils.createLoadingDialog(this, "加载中.....");
            }
            iPersenter.startRequestMoreFile(url,map,list,clazz);
        }
    }


    @Override
    public void getData(Object data) {
        LoadingUtils.closeDialog(loadingDialog);
        success(data);
    }

    @Override
    public void getError(String error) {
        LoadingUtils.closeDialog(loadingDialog);
        failed(error);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isNeedFCancle){
            return super.onTouchEvent(event);
        }else {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                //手指按下时的坐标
                downX = event.getX();

            }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                float moveDistanceX = event.getX()-downX;
                if(moveDistanceX > 0){
                    decorView.setX(moveDistanceX);
                }
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                float moveDistanceX = event.getX()-downX;
                if(moveDistanceX > 0){
                    if(moveDistanceX > screenWidth / 2){
                        ContinueMove(moveDistanceX);
                    }else {
                        reToBackLeft(moveDistanceX);
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void ContinueMove(float moveDistanceX){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(moveDistanceX, screenWidth);
        valueAnimator.setDuration(1000);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue();
                decorView.setX(x);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });

    }

    private void reToBackLeft(float moveDistanceX){
        ObjectAnimator.ofFloat(decorView,"X",moveDistanceX,0)
                .setDuration(1000)
                .start();
    }
}
