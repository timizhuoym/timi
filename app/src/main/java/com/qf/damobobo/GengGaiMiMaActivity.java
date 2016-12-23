package com.qf.damobobo;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qf.damobobo.utils.Shuju;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;

public class GengGaiMiMaActivity extends AppCompatActivity {
    EditText ed_old,ed_new,ed_zai;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geng_gai_mi_ma);
        ed_old=(EditText) findViewById(R.id.ed_yuanmima);
        ed_new=(EditText) findViewById(R.id.ed_newmima);
        ed_zai=(EditText) findViewById(R.id.ed_zai);
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        String pass = sp.getString("pass", "");
        if (!pass.equals("")){
            Shuju.miMa=pass;
        }
    }

    public void myClick(View view) {
        finish();
    }

    public void xiuGai(View view) {
        int k=-1;
        if (ed_old.getText().toString().equals(Shuju.miMa)){
            if (ed_new.getText().toString().matches("[a-zA-Z][0-9a-zA-Z]{5,7}")){
                if (ed_new.getText().toString().equals(ed_zai.getText().toString())){
                    k=4;
                }else {
                    k=3;
                }
            }else {
                k=2;
            }
        }else{
            k=1;
        }

        if (k==1){
            Toast.makeText(this,"原密码输入有误",Toast.LENGTH_SHORT).show();
            return;
        }
        if (k==2){
            Toast.makeText(this,"输入新密码格式有误",Toast.LENGTH_SHORT).show();
            return;
        }
        if (k==3){
            Toast.makeText(this,"两次输入的新密码不同",Toast.LENGTH_SHORT).show();
            return;
        }

            Map<String,String>map=new LinkedHashMap<>();
            map.put("uid",Shuju.userId);
            map.put("oldpass",Shuju.miMa);
            map.put("newpass",ed_new.getText().toString());
            OkHttpUtils.post()
                    .url(Shuju.ZHULUJIN+Shuju.GAI_MI_MA)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(GengGaiMiMaActivity.this,"网络等有问题",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response!=null){
                                try {
                                    JSONObject object=new JSONObject(response.trim().replace("\ufeff",""));
                                    int a=object.optInt("result");
                                    if (a==1){
                                        Toast.makeText(GengGaiMiMaActivity.this,"改密成功",Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor edit = sp.edit();
                                        edit.clear();
                                        edit.apply();

                                        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("userpass",Shuju.Userpass);
                                        editor.putString("username",Shuju.ZhangHao);
                                        editor.putString("usersex",Shuju.sex);
                                        editor.putString("nickname",Shuju.niCheng);
                                        editor.putString("useremail",Shuju.youxiang);
                                        editor.putString("birthday",Shuju.shengRi);
                                        editor.putString("id",Shuju.userId);
                                        editor.putString("name",Shuju.ZhangHao);
                                        editor.putString("pass",ed_new.getText().toString());
                                        edit.putString("portrait",Shuju.touXiang);
                                        edit.putString("signature",Shuju.qianMing);
                                        editor.apply();

                                        SharedPreferences sp1 = getSharedPreferences("sp", MODE_PRIVATE);
                                        Shuju.Userpass=sp1.getString("userpass","");
                                        Shuju.ZhangHao=sp1.getString("username","");
                                        Shuju.sex=sp1.getString("usersex","");
                                        Shuju.niCheng=sp1.getString("nickname","");
                                        Shuju.youxiang=sp1.getString("useremail","");
                                        Shuju.shengRi=sp1.getString("birthday","");
                                        Shuju.miMa=sp1.getString("pass","");
                                        Shuju.userId=sp1.getString("id","");
                                        Shuju.touXiang=sp.getString("portrait","");
                                        Shuju.qianMing=sp.getString("signature","");
                                    }else {
                                        Toast.makeText(GengGaiMiMaActivity.this,"改密失败",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

    }
}
