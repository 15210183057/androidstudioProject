package com.example.a123456.zhonggu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import application.MyApplication;
import base.BaseActivity;
import bean.UserBean;
import jiekou.getInterface;
import utils.SharedUtils;
import View.GetJsonUtils;
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
    StringBuilder stringBuilder;
    private void getLogin(){
        //登陆
        final String username=edt_username.getText().toString();
        final String password=edt_password.getText().toString();
        //判断用户名和密码是否为空
        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
            //登陆，发送请求
            final RequestParams params=new RequestParams(getInterface.loginUser);
            params.addBodyParameter("username",username);
            params.addBodyParameter("pass",password);
            params.addBodyParameter("json","1");

            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.e("TAG","result=="+result);
                   List<UserBean> list= GetJsonUtils.getLogin(LoginActivity.this,result);
                   //打印groups
//                    String str=UserBean.groups.substring(1,UserBean.groups.length());
//                    String str=username.substring(1,username.length());
//                    Log.e("TAG","截图后=="+str);
//                    String laseStr=str;
//                    stringBuilder=new StringBuilder();
//                    Log.e("TAG","字符串=="+str);
//                    String strspil=stringBuilder.append(str+",").toString();
//                    Log.e("TAG","拼接后=="+strspil);
//                    String arr[]=strspil.split(",");
//                    Log.e("TAG","数组=="+arr);
//                    for(int i=0;i<arr.length;i++){
//                        Log.e("TAG","数组值为=="+arr[i].toString());
//                    }
                    //登陆成功，保存用户名和密码到SP，并跳转
                    Log.e("TAG","usname="+username+"==UserBean.username"+UserBean.username);
                    Log.e("TAG","password="+password+"==UserBean.password"+UserBean.password);
                    Log.e("TAG","判断="+(username.equals(UserBean.username)&&password.equals(UserBean.password)));
                    if(UserBean.status.equals("1")) {
                        SharedUtils utils = new SharedUtils();
                        utils.saveXML(MyApplication.usermsg,"username", UserBean.username, LoginActivity.this);
                        utils.saveXML(MyApplication.usermsg,"password", password, LoginActivity.this);
                        utils.saveXML(MyApplication.usermsg,"groupids", UserBean.groupids, LoginActivity.this);
                        utils.saveXML(MyApplication.usermsg,"userid",UserBean.id,LoginActivity.this);
                        Intent intent = new Intent(LoginActivity.this, FrameActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
            Toast.makeText(LoginActivity.this,"点击登陆",Toast.LENGTH_SHORT).show();


        }else{
            Toast.makeText(LoginActivity.this,"用户名或密码不可为空",Toast.LENGTH_SHORT).show();
        }
    }
}
