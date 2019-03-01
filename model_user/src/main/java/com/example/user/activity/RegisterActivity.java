package com.example.user.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.component.base.activity.BaseNetworkActivity;
import com.example.component.utils.RegexUtils;
import com.example.component.utils.ToastUtils;
import com.example.user.Apis;
import com.example.user.R;
import com.example.user.RSA.RsaCoder;
import com.example.user.bean.RegisterBean;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseNetworkActivity implements View.OnClickListener {

    EditText register_name_edittext;
    EditText register_phone_editText;
    EditText register_pwd_edittext;
    Button register_button;
    private String password;
    private TextView textView;

    @Override
    public int intiLayout() {
        return R.layout.model_user_activity_register;
    }

    @Override
    public void initView() {
        register_name_edittext=findViewById(R.id.model_user_activity_register_name_edittext);
        register_phone_editText=findViewById(R.id.model_user_activity_register_phone_editText);
        register_pwd_edittext=findViewById(R.id.model_user_activity_register_pwd_edittext);
        register_button=findViewById(R.id.model_user_activity_register_button);
        textView = findViewById(R.id.model_user_activity_register_message);
        isNeedFCancle=false;
    }

    @Override
    public void initData() {
        register_button.setOnClickListener(this);
    }

    @Override
    public void success(Object data) {
        if(data instanceof RegisterBean){
            RegisterBean registerBean= (RegisterBean) data;
            if(registerBean.getStatus().equals("0000")){
                ToastUtils.toast(registerBean.getMessage());
            }else{
                ToastUtils.toast(registerBean.getMessage());
            }
        }
    }

    @Override
    public void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.model_user_activity_register_button){
            String name = register_name_edittext.getText().toString();
            String phone = register_phone_editText.getText().toString();
            String pwd = register_pwd_edittext.getText().toString();
            try {
                password = RsaCoder.encryptByPublicKey(pwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!RegexUtils.isMobile(phone)){
                ToastUtils.toast("格式不正确");
            }else if(!RegexUtils.isPassword(pwd)){
                ToastUtils.toast("格式不正确");
            }else {
                Map<String, String> map = new HashMap<>();
                map.put("phone",phone );
                map.put("nickName", name);
                map.put("pwd", password);
                startRequestDataPost(String.format(Apis.URL_USER_REGISTER),map,RegisterBean.class);
            }
        }else if(id==R.id.model_user_activity_register_message){

        }
    }
}
