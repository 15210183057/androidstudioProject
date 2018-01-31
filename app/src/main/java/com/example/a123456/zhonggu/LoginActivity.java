package com.example.a123456.zhonggu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import application.MyApplication;
import base.BaseActivity;
import utils.SharedUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText edt_username;
    private EditText edt_password;
    private Button btn_login;
    private TextView tv_password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        edt_username=findViewById(R.id.edt_username);
        edt_password=findViewById(R.id.edt_password);
        tv_password2=findViewById(R.id.tv_password2);
        //动态设置输入框的类型
//        edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tv_password2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                getLogin();
                break;
            case R.id.tv_password2:
                Toast.makeText(LoginActivity.this,"忘记密码",Toast.LENGTH_SHORT).show();
                break;
        }

    }
    private void getLogin(){
        //登陆
        String username=edt_username.getText().toString();
        String password=edt_password.getText().toString();
        //判断用户名和密码是否为空
        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
            //登陆，发送请求
            Toast.makeText(LoginActivity.this,"点击登陆",Toast.LENGTH_SHORT).show();

            //登陆成功，保存用户名和密码到SP，并跳转
            SharedUtils utils=new SharedUtils();
            utils.saveXML("username",username,LoginActivity.this);
            utils.saveXML("password",password,LoginActivity.this);
            Intent intent=new Intent(LoginActivity.this,FrameActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(LoginActivity.this,"用户名或密码不可为空",Toast.LENGTH_SHORT).show();
        }
    }
}
