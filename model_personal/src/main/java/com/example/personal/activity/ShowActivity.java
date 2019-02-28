package com.example.personal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.component.base.activity.BaseActivity;
import com.example.personal.R;
import com.example.personal.fragment.CommendFragment;
import com.example.personal.fragment.CommunityFragment;
import com.example.personal.fragment.GroupMessageFragment;
import com.example.personal.fragment.LeftPersonalFragment;

public class ShowActivity extends BaseActivity {
    private RadioGroup radioGroup;
    private CommendFragment commendFragment;
    private GroupMessageFragment groupMessageFragment;
    private CommunityFragment communityFragment;
    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ActionBar actionBar;


    @Override
    public int intiLayout() {
        return R.layout.personal_activity_show;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        radioGroup = findViewById(R.id.personal_radioGroup_change);
        drawerLayout=findViewById(R.id.drawer);
        //加载布局
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame,new LeftPersonalFragment())
                    .commit();
        }
    }


    @Override
    public void initData() {

        actionBar=getSupportActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(true);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.kai,R.string.guan);
        toggle.syncState();
        //  drawerLayout.addDrawerListener(toggle);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //滑动过程中不断回调 slideOffset:0~1
                View content = drawerLayout.getChildAt(0);
                View menu = drawerView;

                float scale = 1 - slideOffset;//1~0
                content.setTranslationX(menu.getMeasuredWidth() * (1 - scale));//0~width
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        fragmentManager = getSupportFragmentManager();

        //将所有Fragment添加到占位布局
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        commendFragment = new CommendFragment();
        fragmentTransaction
                .add(R.id.frameLayout, commendFragment)
                .commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hidefragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if(checkedId==R.id.radioButton_comment){
                    fragmentTransaction.show(commendFragment).commit();

                }else if(checkedId==R.id.radioButton_message){
                    if (groupMessageFragment == null) {
                        groupMessageFragment = new GroupMessageFragment();
                        fragmentTransaction.add(R.id.frameLayout, groupMessageFragment).commit();
                    } else {
                        fragmentTransaction.show(groupMessageFragment).commit();
                    }


                }else if(checkedId==R.id.radioButton_community){
                    if(communityFragment==null)
                    {
                        communityFragment = new CommunityFragment();
                        fragmentTransaction.add(R.id.frameLayout,communityFragment).commit();
                    }else
                    {
                        fragmentTransaction.show(communityFragment).commit();
                    }

                }
            }
        });

    }
    //隐藏Fragment
    private void hidefragment(){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (commendFragment != null && commendFragment.isAdded()) {
            fragmentTransaction.hide(commendFragment);
        }
        if (groupMessageFragment != null && groupMessageFragment.isAdded()) {
            fragmentTransaction.hide(groupMessageFragment);
        }
        if (communityFragment != null && communityFragment.isAdded()) {
            fragmentTransaction.hide(communityFragment);
        }

        fragmentTransaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
