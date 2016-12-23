package com.qf.damobobo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qf.damobobo.utils.Shuju;

public class GerenzhongxinActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE = 2;
    private RelativeLayout nicheng;
    private RelativeLayout sex;
    private RelativeLayout youxiang;
    private RelativeLayout shengri;
    private TextView tv_zhanghao;
    private TextView tv_nicheng;
    private ImageView img;
    private TextView tv_sex;
    private TextView tv_youxiang;
    private TextView tv_shengri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenzhongxin);
        initViews();
        setListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        tv_zhanghao.setText(Shuju.ZhangHao);
        tv_nicheng.setText(Shuju.niCheng);
        if (!Shuju.sex.equals("")){
            if (Shuju.sex.equals("0")){
                img.setImageResource(R.drawable.icon_man);
                tv_sex.setText("男");
            }else {
                img.setImageResource(R.drawable.icon_woman);
                tv_sex.setText("女");
            }
        }else {
            img.setImageResource(R.drawable.icon_mine);
            tv_sex.setText("未填");
        }
        tv_youxiang.setText(Shuju.youxiang);
        tv_shengri.setText(Shuju.shengRi);
    }

    private void setListener() {
        nicheng.setOnClickListener(this);
        youxiang.setOnClickListener(this);
        sex.setOnClickListener(this);
        shengri.setOnClickListener(this);
    }

    private void initViews() {
        nicheng = (RelativeLayout) findViewById(R.id.re_nicheng);
        sex = (RelativeLayout) findViewById(R.id.re_sex);
        youxiang = (RelativeLayout) findViewById(R.id.re_youxiang);
        shengri = (RelativeLayout) findViewById(R.id.re_shengri);
        tv_zhanghao = (TextView) findViewById(R.id.tv_zhanghao);
        tv_nicheng = (TextView) findViewById(R.id.tv_nicheng);
        img = (ImageView) findViewById(R.id.img);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_youxiang = (TextView) findViewById(R.id.tv_youxiang);
        tv_shengri = (TextView) findViewById(R.id.tv_shengri);
    }

    public void myClick(View view) {
        finish();
    }

    public void xiuGai(View view) {
        startActivity(new Intent(this,GengGaiMiMaActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_nicheng:
                startActivity(new Intent(GerenzhongxinActivity.this,NiChengActivity.class));
                break;
            case R.id.re_sex:
                startActivity(new Intent(GerenzhongxinActivity.this,SexActivity.class));
                break;
            case R.id.re_youxiang:
                startActivity(new Intent(GerenzhongxinActivity.this,YouxiangActivity.class));
                break;
            case R.id.re_shengri:
                startActivity(new Intent(GerenzhongxinActivity.this,ShengriActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE&&resultCode== RESULT_CODE){

        }
    }
}
