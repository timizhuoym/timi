package com.qf.damobobo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.qf.damobobo.utils.SDUtil;
import com.qf.damobobo.utils.Shuju;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class RegistActivity extends AppCompatActivity {

    private ImageView img;
    private EditText zh;
    private EditText name;
    private EditText pass;
    int k=0;
    String hao;
    String user;
    String word;
    private Uri photoUri;
    private String picPath;
    Intent lastIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initViews();
    }

    private void panDuan() {
        if (!zh.getText().toString().replace(" ","").equals("")
                &&!name.getText().toString().replace(" ","").equals("")
                &&!pass.getText().toString().replace(" ","").equals("")){
            hao = zh.getText().toString();
            user = name.getText().toString();
            word = pass.getText().toString();
            if (hao.matches("[a-zA-Z0-9]{6,9}")) {
                if (user.length()>=6&&user.length()<=9) {
                    if (word.matches("[a-zA-Z][a-zA-z0-9]{5,7}")){
                        k=3;
                    }else {
                        k=2;
                    }
                }else {
                    k=2;
                }
            }else {
                k=2;
            }
        }else {
            k=1;
        }

    }


    private void initViews() {
        img = (ImageView) findViewById(R.id.img);
        zh = (EditText) findViewById(R.id.ed_zh);
        name = (EditText) findViewById(R.id.ed_userName);
        pass = (EditText) findViewById(R.id.ed_pass);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
    }

    public void myClick_rigist(View view) {
        panDuan();
        if (k==1){
            Toast.makeText(this,"内容不能有空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (k==2){
            Toast.makeText(this,"注册格式不符合！",Toast.LENGTH_SHORT).show();
            return;
        }
        if (k==3){
            if (picPath==null){
                Toast.makeText(RegistActivity.this,"请先选择图片",Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String,String>map=new LinkedHashMap<>();
            map.put("nikename",user);
            map.put("username",hao);
            map.put("password",word);
            String fileName=picPath.split("/")[picPath.split("/").length-1];
            OkHttpUtils.post()
                    .addFile("portrait",fileName,new File(picPath))
                    .url(Shuju.ZHULUJIN+Shuju.REGIST)
                    .params(map)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Toast.makeText(RegistActivity.this,"网络有问题!",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            JSONObject object;
                            if (response!=null){
                                try {
                                    object = new JSONObject(response.trim().replace("\ufeff",""));
                                    int result = object.optInt("result");
                                    if (result==1){
                                        Toast.makeText(RegistActivity.this,"恭喜，注册成功！",Toast.LENGTH_SHORT).show();
                                        Log.i("info--","恭喜，注册成功！");
                                        Intent intent=new Intent(RegistActivity.this,LoginActivity.class);
                                        intent.putExtra("name",hao);
                                        intent.putExtra("pass",word);
                                        RegistActivity.this.startActivity(intent);
                                        RegistActivity.this.finish();
                                    }else{
                                        Log.i("info--","注册失败!账号可能重复");
                                        Toast.makeText(RegistActivity.this,"注册失败!",Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });


        }
    }

    public void myClick(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        doPhoto(requestCode,data);
            if(data==null){
                return;//当data为空的时候，不做任何处理
            }
            Bitmap bitmap = null;
            if(requestCode==0){
                //获取从相册界面返回的缩略图
                bitmap = data.getParcelableExtra("data");
                if(bitmap==null){//如果返回的图片不够大，就不会执行缩略图的代码，因此需要判断是否为null,如果是小图，直接显示原图即可
                    try {
                        //通过URI得到输入流
                        InputStream inputStream = getContentResolver().openInputStream(data.getData());

                        //通过输入流得到bitmap对象
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else if(requestCode==1){
                bitmap = (Bitmap) data.getExtras().get("data");
                saveToSDCard(bitmap);
            }
            //将选择的图片设置到控件上
            img.setImageBitmap(bitmap);
    }
    /**
     * 把拍的照片保存到SD卡
     */
    public void saveToSDCard(Bitmap bitmap) {
        //先要判断SD卡是否存在并且挂载
        if (SDUtil.isMounted()) {
            File file = new File(Shuju.iconPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(createPhotoName());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);//把图片数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if(outputStream!=null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            Toast.makeText(this,"SD卡不存在",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 给拍的照片命名
     */
    public String createPhotoName(){
        //以系统的当前时间给图片命名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = format.format(date)+".jpg";
        return fileName;
    }
    /**
     * 选择图片后，获取图片的路径
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode,Intent data)
    {
        if(requestCode == 0 ) //从相册取图片，有些手机有异常情况，请注意
        {
            if(data == null)
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            if(photoUri == null )
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null,null);
        if(cursor != null )
        {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            cursor.close();
        }
        Log.i("TAG", "imagePath = "+picPath);
    }

}
