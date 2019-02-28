package com.example.community.fragment;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.community.R;
import com.example.component.base.fragment.BaseNetworkFragment;

@Route(path = "/model_community/CommunityFragment")
public class CommunityFragment extends BaseNetworkFragment {

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getViewById() {
        return R.layout.community_fragment;
    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {

    }
}
