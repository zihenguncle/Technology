package com.example.component.mvp.callback;

import android.animation.ValueAnimator;

public interface ICallBack<T> {
    void getData(T data);
    void getError(String error);
}
