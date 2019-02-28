package com.example.inforecommend.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component.base.fragment.BaseNetworkFragment;
import com.example.inforecommend.R;


@Route(path = "/model_inforecommend/CommendFragment")
public class CommendFragment extends BaseNetworkFragment {


    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected int getViewById() {
        return R.layout.commend_fragment;
    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {

    }
}
