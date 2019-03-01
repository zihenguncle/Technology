package com.example.user.activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.component.base.activity.BaseNetworkActivity;
import com.example.component.utils.RegexUtils;
import com.example.component.utils.SpUtils;
import com.example.component.utils.ToastUtils;
import com.example.user.Apis;
import com.example.user.R;
import com.example.user.RSA.RsaCoder;
import com.example.user.bean.LoginBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends BaseNetworkActivity implements View.OnClickListener{
    TextView textView_register;
    private boolean isHideFirst = true;
    EditText pwd_hide_show;
    EditText editText_phone;
    ImageView login_hide_show;
    Button login_button;
    private String password;

    @Override
    public int intiLayout() {
        return R.layout.model_user_activity_login;
    }

    @Override
    public void initView() {
        login_hide_show=findViewById(R.id.model_user_activity_login_hide_show);
        textView_register=findViewById(R.id.model_user_activity_login_textView_register);
        pwd_hide_show=findViewById(R.id.model_user_activity_login_pwd_editText);
        editText_phone=findViewById(R.id.model_user_activity_login_editText_phone);
        login_button=findViewById(R.id.model_user_activity_login_button_begin);
        isNeedFCancle=false;
    }

    @Override
    public void initData() {
        textView_register.setOnClickListener(this);
        login_hide_show.setOnClickListener(this);
        login_button.setOnClickListener(this);
    }

    @Override
    public void success(Object data) {
        if(data instanceof LoginBean){
            LoginBean loginBean= (LoginBean) data;
            if(loginBean.getStatus().equals("0000")){
                ToastUtils.toast(loginBean.getMessage());
                LoginBean.ResultBean result = loginBean.getResult();
                int userId = result.getUserId();
                String sessionId = result.getSessionId();
                SpUtils spUtils = new SpUtils("User", this);
                spUtils.putString("userId",userId+"");
                spUtils.putString("sessionId",sessionId);
                finish();
            }else{
                ToastUtils.toast(loginBean.getMessage());
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
        if(id==R.id.model_user_activity_login_textView_register){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }else if(id==R.id.model_user_activity_login_hide_show){
            if (isHideFirst == true) {
                HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                pwd_hide_show.setTransformationMethod(method1);
                isHideFirst = false;
            } else {
                TransformationMethod method = PasswordTransformationMethod.getInstance();
                pwd_hide_show.setTransformationMethod(method);
                isHideFirst = true;
            }
                int index = pwd_hide_show.getText().toString().length();
                pwd_hide_show.setSelection(index);
        }else if(id==R.id.model_user_activity_login_button_begin){
            String phone = editText_phone.getText().toString();
            String pwd = pwd_hide_show.getText().toString();
            try {
                password = RsaCoder.encryptByPublicKey(pwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!RegexUtils.isMobile(phone)){
                ToastUtils.toast("手机号格式不正确");
            }else if(!RegexUtils.isPassword(pwd)){
                ToastUtils.toast("密码格式不正确");
            }else{
                Map<String,String> map=new HashMap<>();
                map.put("phone",phone);
                map.put("pwd",password);
                startRequestDataPost(Apis.URL_USER_LOGIN,map,LoginBean.class);
            }
        }
    }
}
