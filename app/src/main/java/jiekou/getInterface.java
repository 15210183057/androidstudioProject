package jiekou;

/**
 * Created by 123456 on 2018/2/5.
 */

public class getInterface {
    //登陆接口http://test.zgcw.cn/api/api_user/userlogin?username=wangsf&pass=wangsf&json=1
//    {"status":1,"user":{"id":"2","username":"admin","password":"8940249fbb327af9b5ee2836425cdbc2","headimg":"20170918\\732f97fa0c543a3d1150d7b1ee302631.jpg","loginip":"127.0.0.1","logintime":"1478751964","groupid":"0","status":"1"},"groups":[0]}
    public static String loginUser="http://test.zgcw.cn/api/api_user/userlogin?";
    //待补充车源
//    http://test.zgcw.cn/api/api_car_select/getCarList
// 参数格式：json=1&pagesize=10&where=blu=1 and groupid in(5,3,2) and status = 1
    public static String getBuCartList="http://test.zgcw.cn/api/api_car_select/getCarList";
}
