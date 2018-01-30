package base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import utils.StatusBarCompat;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //        继承AppCompatActivity时使用：
//        supportRequestWindowFeature(Window.FEATURENOTITLE)
//
//        继承activity时使用：
//        requestWindowFeature(Window.FEATURENOTITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this, Color.RED);
    }
}
