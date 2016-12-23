package com.qf.damobobo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qf.damobobo.utils.Shuju;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public class SexActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int RESULT_CODE = 2;
    private RelativeLayout nan;
    private RelativeLayout nv;
    private ImageButton img_nan;
    private ImageButton img_nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        initViews();
        setListener();
        Intent intent = getIntent();
        if (!Shuju.sex.equals("")){
            if (Shuju.sex.equals("0")){
                img_nv.setVisibility(View.INVISIBLE);
            }else {
                img_nan.setVisibility(View.INVISIBLE);
            }
        }else {
            img_nan.setVisibility(View.INVISIBLE);
            img_nv.setVisibility(View.INVISIBLE);
        }
    }

    private void setListener() {
        nan.setOnClickListener(this);
        nv.setOnClickListener(this);
    }

    private void initViews() {
        nan = (RelativeLayout) findViewById(R.id.re_nan);
        nv = (RelativeLayout) findViewById(R.id.re_nv);
        img_nan = (ImageButton) findViewById(R.id.img_nan);
        img_nv = (ImageButton) findViewById(R.id.img_nv);
    }

    public void myClick(View view) {
        if (img_nan.getVisibility()==View.INVISIBLE&&img_nv.getVisibility()==View.INVISIBLE){
            Toast.makeText(SexActivity.this, "请先选择一个", Toast.LENGTH_SHORT).show();
        }else {
            String a;
            if (img_nan.getVisibility()==View.VISIBLE){
                a=0+"";
            }else {
                a=1+"";
            }
            final String b=a;
            OkHttpUtils.post()
                    .url(Shuju.ZHULUJIN+Shuju.GAI_SEX)
                    .addParams("uid",Shuju.userId)
                    .addParams("userpass",Shuju.Userpass)
                    .addParams("usersex",a)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(SexActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response!=null){
                                try {
                                    if (new JSONObject(response.trim().replace("\ufeff","")).optInt("result")==1){
                                        Toast.makeText(SexActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor edit = getSharedPreferences("sp", MODE_PRIVATE).edit();
                                        edit.clear();
                                        edit.apply();
                                        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("userpass",Shuju.Userpass);
                                        editor.putString("username",Shuju.ZhangHao);
                                        editor.putString("usersex",b);
                                        editor.putString("nickname",Shuju.niCheng);
                                        editor.putString("useremail",Shuju.youxiang);
                                        editor.putString("birthday",Shuju.shengRi);
                                        editor.putString("id",Shuju.userId);
                                        editor.putString("name",Shuju.ZhangHao);
                                        editor.putString("pass",Shuju.Userpass);
                                        edit.putString("portrait",Shuju.touXiang);
                                        edit.putString("signature",Shuju.qianMing);
                                        editor.apply();

                                        Shuju.saveData(getSharedPreferences("sp",MODE_PRIVATE));
                                        SexActivity.this.finish();
                                    }else {
                                        Toast.makeText(SexActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.re_nan:
                img_nan.setVisibility(View.VISIBLE);
                img_nv.setVisibility(View.INVISIBLE);
                break;
            case R.id.re_nv:
                img_nv.setVisibility(View.VISIBLE);
                img_nan.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
