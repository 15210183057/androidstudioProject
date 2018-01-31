package application;

import android.app.Application;
import android.content.SharedPreferences;

import org.xutils.x;

import bean.UserBean;
import utils.SharedUtils;

public class MyApplication extends Application {
//    public static String username;
//    public static String password;
    public static MyApplication myApplication;
    public UserBean userBean=new UserBean();
    public SharedUtils sharedUtils;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        sharedUtils=new SharedUtils();
        userBean.username= sharedUtils.readXML("username",this);
        userBean.password= sharedUtils.readXML("password",this);
    }
    public static MyApplication getInstance(){
        myApplication=new MyApplication();
        return myApplication;
    }
}
