package com.qf.damobobo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qf.damobobo.adapter.MyViewPagerAdapter;
import com.qf.damobobo.fragment.LeftFragment;
import com.qf.damobobo.fragment.RightFragment;
import com.qf.damobobo.utils.Shuju;

import java.util.ArrayList;
import java.util.List;

public class ShouActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
        View.OnClickListener,ViewPager.OnPageChangeListener,DrawerLayout.DrawerListener{

    private RadioGroup rgb;
    private RadioButton rb1;
    private RadioButton rb2;
    private DrawerLayout drawerLayout;
    private LinearLayout wangshi;
    private LinearLayout gushi;
    private LinearLayout jilu;
    private LinearLayout xinxi;
    private LinearLayout shezhi;
    private Button back;
    private ViewPager pager;
    List<Fragment>data=new ArrayList<>();
    private ImageButton img_left;
    private ImageButton img_right;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou);
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        Shuju.activities.add(this);
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
        initViews();

        rgb.setOnCheckedChangeListener(this);
        rb1 = (RadioButton) findViewById(R.id.rb1);
        rb2 = (RadioButton) findViewById(R.id.rb2);
        rb1.setChecked(true);
        rb1.setBackgroundColor(Color.rgb(255,255,255));
        rb2.setBackgroundColor(Color.rgb(50,100,163));

        setLinstener();
    }



    private void setLinstener() {
        data.add(new LeftFragment());
        data.add(new RightFragment());
        wangshi.setOnClickListener(this);
        gushi.setOnClickListener(this);
        jilu.setOnClickListener(this);
        xinxi.setOnClickListener(this);
        shezhi.setOnClickListener(this);
        back.setOnClickListener(this);
        pager.addOnPageChangeListener(this);
        MyViewPagerAdapter adapter=new MyViewPagerAdapter(getSupportFragmentManager(),data);
        pager.setAdapter(adapter);
        drawerLayout.addDrawerListener(this);
    }

    private void initViews() {
        rgb = (RadioGroup) findViewById(R.id.rgb_xuanze);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        wangshi = (LinearLayout) findViewById(R.id.wangshi);
        gushi = (LinearLayout)findViewById(R.id.wdgushi);
        jilu = (LinearLayout) findViewById(R.id.jilu);
        xinxi = (LinearLayout) findViewById(R.id.xinxi);
        shezhi = (LinearLayout) findViewById(R.id.shezhi);

        back = (Button)findViewById(R.id.back);
        pager = (ViewPager) findViewById(R.id.pager);
        img_left = (ImageButton) findViewById(R.id.img_left);
        img_right = (ImageButton) findViewById(R.id.img_right);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i <group.getChildCount() ; i++) {
            RadioButton rb= (RadioButton) group.getChildAt(i);
            if (rb.isChecked()){
                pager.setCurrentItem(i);
            }
        }


        switch (checkedId){
            case R.id.rb1:
                rb1.setBackgroundColor(Color.rgb(255,255,255));
                rb2.setBackgroundColor(Color.rgb(50,100,163));
                break;
            case R.id.rb2:
                rb2.setBackgroundColor(Color.rgb(255,255,255));
                rb1.setBackgroundColor(Color.rgb(50,100,163));
                break;
        }
    }

    public void myClick_right(View view) {
        startActivity(new Intent(this,GushiActivity.class));
    }

    public void myClick_left(View view) {
        drawerLayout.openDrawer(Gravity.LEFT,true);
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()){
                case R.id.back:
                    SharedPreferences.Editor edit = sp.edit();
                    edit.clear();
                    edit.apply();
                    for (AppCompatActivity activity:Shuju.activities){
                        activity.finish();
                    }
                    break;
                case R.id.wangshi:
                    drawerLayout.closeDrawer(Gravity.LEFT,true);
                    break;
                case R.id.wdgushi:
                    ShouActivity.this.startActivity(new Intent(ShouActivity.this,MyGushiActivity.class));
                    break;
                case R.id.jilu:
                    break;
                case R.id.xinxi:
                    startActivity(new Intent(ShouActivity.this,GerenzhongxinActivity.class));
                    break;
                case R.id.shezhi:
                    startActivity(new Intent(ShouActivity.this,SettingActivity.class));
                    break;

            }
        }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        RadioButton rb= (RadioButton) rgb.getChildAt(position);
        rb.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        rgb.setFocusable(false);
        img_left.setFocusable(false);
        img_right.setFocusable(false);
        pager.setFocusable(false);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        rgb.setFocusable(true);
        img_left.setFocusable(true);
        img_right.setFocusable(true);
        pager.setFocusable(true);
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }
}
