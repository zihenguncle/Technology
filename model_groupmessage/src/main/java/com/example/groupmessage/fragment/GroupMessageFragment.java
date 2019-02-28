package com.example.groupmessage.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component.base.fragment.BaseNetworkFragment;
import com.example.groupmessage.R;


@Route(path = "/model_groupmessage/GroupMessageFragment")
public class GroupMessageFragment extends BaseNetworkFragment {

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getViewById() {
        return R.layout.group_fragment;
    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {

    }
}
