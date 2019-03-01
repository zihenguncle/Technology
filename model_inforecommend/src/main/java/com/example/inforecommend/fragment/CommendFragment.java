package com.example.inforecommend.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.component.base.fragment.BaseNetworkFragment;
import com.example.component.utils.ToastUtils;
import com.example.inforecommend.CommendApis;
import com.example.inforecommend.R;
import com.example.inforecommend.bean.BannerBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.XBanner;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Route(path = "/model_inforecommend/CommendFragment")
public class CommendFragment extends BaseNetworkFragment {



    private ImageView imageView_commend_menu,imageView_commend_search;
    private XBanner xBanner;

    @Override
    protected void initData() {
        //轮播图
        bannerView();

    }

    private void bannerView() {

       startRequestDataGet(CommendApis.URL_BANNER, BannerBean.class);

    }

    @Override
    protected void initView(View view) {
        xBanner = view.findViewById(R.id.commend_xbanner);
        imageView_commend_menu = view.findViewById(R.id.commend_menu);
        imageView_commend_search=view.findViewById(R.id.commend_search);

    }

    @Override
    protected int getViewById() {
        return R.layout.commend_fragment;
    }

    @Override
    protected void successed(Object data) {

        if(data instanceof BannerBean){
           final BannerBean bannerBean= (BannerBean) data;
            if(bannerBean.getStatus().equals("0000")){
                List<String> titleList = new ArrayList<>();
                for (int i=0;i<bannerBean.getResult().size();i++)
                {
                    titleList.add(bannerBean.getResult().get(i).getTitle());
                }
                xBanner.setData(R.layout.fresco_imageview,bannerBean.getResult(),titleList);

                xBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        BannerBean.ResultBean bean= (BannerBean.ResultBean) model;
                        SimpleDraweeView draweeView = (SimpleDraweeView) view;
                        draweeView.setImageURI(bean.getImageUrl());


                    }
                });

            }else{
                Toast.makeText(getContext(), bannerBean.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    protected void failed(String error) {


    }

}
