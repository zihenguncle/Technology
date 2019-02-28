package com.example.inforecommend.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.component.base.fragment.BaseNetworkFragment;
import com.example.component.utils.ToastUtils;
import com.example.inforecommend.R;

public class InfoRecommendFragment extends BaseNetworkFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        Fragment fragment= (Fragment) ARouter.getInstance().build("/model_personal/CommendFragment").navigation();
        if (fragment != null) {
            ToastUtils.toast("show");
            FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.relative_commend, fragment);
            transaction.commit();
        } else {
            ToastUtils.toast("hide");
        }


    }

    @Override
    protected int getViewById() {
        return R.layout.commend_main;
    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {

    }
}
