package com.qf.damobobo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TimiZhuo on 2016/12/6.
 */
public class Shuju {
    public static List<AppCompatActivity>activities=new ArrayList<>();
    public static final String iconPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Image";//图片的存储目录
    public static final String ZHULUJIN="http://139.129.19.51/story/index.php/home/Interface/";
    public static final String REGIST="regist";
    public static final String LOGIN="login";
    public static final String GETSTORY="getStorys";
    public static final String TOUIMGLUJIN="http://139.129.19.51/story/Uploads/portrait/";
    public static final String TUPIAN="http://139.129.19.51/story/Uploads/";
    public static final String GAI_MI_MA="changePassword";
    public static final String WDGUSHI="myStorys";
    public static final String GAI_NI_CHENG="changeNickName";
    public static final String GAI_SEX="changeSex";
    public static final String GAI_YOU_XIANG="changeEmail";
    public static final String GAI_SHENG_RI="changeBirthday";
    public static final String GAI_TOU_XIANG="changePortrait";
    public static final String GAI_QIAN_MING="changeSignature";

    public static String ZhangHao;
    public static String Userpass;         //唯一标识
    public static String sex;
    public static String niCheng;
    public static String youxiang;
    public static String shengRi;
    public static String miMa;
    public static String userId;
    public static String touXiang;
    public static String qianMing;

    //getSharedPreferences("sp", context.MODE_PRIVATE);
    public static  void saveData(SharedPreferences sp){
        Shuju.Userpass=sp.getString("userpass","");
        Shuju.ZhangHao=sp.getString("username","");
        Shuju.sex=sp.getString("usersex","");
        Shuju.niCheng=sp.getString("nickname","");
        Shuju.youxiang=sp.getString("useremail","");
        Shuju.shengRi=sp.getString("birthday","");
        Shuju.miMa=sp.getString("pass","");
        Shuju.userId=sp.getString("id","");
        Shuju.touXiang=sp.getString("portrait","");
        Shuju.qianMing=sp.getString("signature","");
    }




}
