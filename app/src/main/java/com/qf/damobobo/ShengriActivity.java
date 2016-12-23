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

import okhttp3.Call;

public class ShengriActivity extends AppCompatActivity {

    private EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shengri);
        ed = (EditText) findViewById(R.id.ed);
        ed.setText(Shuju.shengRi);
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.setText("");
                ed.setHint("请输入年月日");
            }
        });
    }

    public void myClick(View view) {
        finish();
    }

    public void myClick2(View view) {
        final String s = ed.getText().toString();
        OkHttpUtils.post()
                .url(Shuju.ZHULUJIN+Shuju.GAI_SHENG_RI)
                .addParams("uid",Shuju.userId)
                .addParams("userpass",Shuju.Userpass)
                .addParams("birthday",s)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(ShengriActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                if (response!=null){
                    try {
                        if(new JSONObject(response.trim().replace("\ufeff","")).optInt("result")==1){
                            Toast.makeText(ShengriActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor edit = getSharedPreferences("sp", MODE_PRIVATE).edit();
                            edit.clear();
                            edit.apply();
                            SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("userpass",Shuju.Userpass);
                            editor.putString("username",Shuju.ZhangHao);
                            editor.putString("usersex",Shuju.sex);
                            editor.putString("nickname",Shuju.niCheng);
                            editor.putString("useremail",Shuju.youxiang);
                            editor.putString("birthday",s);
                            editor.putString("id",Shuju.userId);
                            editor.putString("name",Shuju.ZhangHao);
                            editor.putString("pass",Shuju.Userpass);
                            edit.putString("portrait",Shuju.touXiang);
                            edit.putString("signature",Shuju.qianMing);
                            editor.apply();

                            Shuju.saveData(getSharedPreferences("sp",MODE_PRIVATE));
                            ShengriActivity.this.finish();
                        }else {
                            Toast.makeText(ShengriActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
