package com.qf.damobobo;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private EditText name;
    private EditText pass;
    private SharedPreferences sp;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("sp", MODE_PRIVATE);
        intent = getIntent();
        Shuju.activities.add(this);
        name = (EditText) findViewById(R.id.ed_userName);
        pass = (EditText) findViewById(R.id.ed_pass);
        if (intent!=null&&intent.getStringExtra("name")!=null&&intent.getStringExtra("pass")!=null){
            name.setText(intent.getStringExtra("name"));
            pass.setText(intent.getStringExtra("pass"));
        }
        if (!sp.getString("name","w").equals("w")&&!sp.getString("pass","s").equals("s")){
            LoginActivity.this.startActivity(new Intent(LoginActivity.this,ShouActivity.class));
            finish();
            return;
        }
    }

    public void myClick(View view) {
        finish();
    }

    public void myClick_zhuce(View view) {
        startActivity(new Intent(this,RegistActivity.class));
    }

    public void myClick_login(View view) {
        int k;
        if (name.getText().toString().matches("[0-9a-zA-Z]{6,9}")){
            if (pass.getText().toString().matches("[a-zA-Z][0-9a-zA-Z]{5,7}")){
                k=2;
            }else {
                k=1;
            }
        }else {
            k=1;
        }

        if (k==2){
            Map<String,String>map=new LinkedHashMap<>();
            map.put("username",name.getText().toString());
            map.put("password",pass.getText().toString());
            OkHttpUtils.post()
                    .url(Shuju.ZHULUJIN+Shuju.LOGIN)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(LoginActivity.this,"网络等问题注册失败!",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            if (response!=null){
                                try {
                                    JSONObject object=new JSONObject(response.trim().replace("\ufeff",""));
                                    int result = object.optInt("result");
                                    if (result==1){
                                        JSONObject jsonObject = object.optJSONObject("data");
                                        SharedPreferences.Editor edit = sp.edit();
                                        edit.putString("name",name.getText().toString());
                                        edit.putString("pass",pass.getText().toString());
                                        edit.putString("userpass",jsonObject.optString("userpass"));
                                        edit.putString("username",jsonObject.optString("username"));
                                        edit.putString("usersex",jsonObject.optString("usersex"));
                                        edit.putString("nickname",jsonObject.optString("nickname"));
                                        edit.putString("useremail",jsonObject.optString("useremail"));
                                        edit.putString("birthday",jsonObject.optString("birthday"));
                                        edit.putString("id",jsonObject.optString("id"));
                                        edit.putString("portrait",jsonObject.optString("portrait"));
                                        edit.putString("signature",jsonObject.optString("signature"));
                                        edit.apply();
                                        Toast.makeText(LoginActivity.this,"恭喜登陆成功！",Toast.LENGTH_SHORT).show();
                                        LoginActivity.this.startActivity(new Intent(LoginActivity.this,ShouActivity.class));
                                        LoginActivity.this.finish();
                                    }else {
                                        Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
            return;
        }
        if (k==1){
            Toast.makeText(LoginActivity.this,"格式不正确",Toast.LENGTH_SHORT).show();
        }
    }
}
