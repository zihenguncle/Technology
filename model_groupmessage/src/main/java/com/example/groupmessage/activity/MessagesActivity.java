package com.example.groupmessage.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.component.base.activity.BaseNetworkActivity;
import com.example.component.utils.ToastUtils;
import com.example.groupmessage.R;

public class MessagesActivity extends BaseNetworkActivity {

    RelativeLayout relativeLayout;

    @Override
    public int intiLayout() {
        return R.layout.activity_messages;
    }

    @Override
    public void initView() {
        relativeLayout = findViewById(R.id.layout);
    }

    @Override
    public void initData() {
        Fragment fragment = (Fragment) ARouter.getInstance().build("/model_personal/CommendFragment").navigation();

        if (fragment != null) {
            ToastUtils.toast("show");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.layout, fragment);
            transaction.commit();
        } else {
            ToastUtils.toast("hide");
        }
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void failed(String error) {

    }
}
