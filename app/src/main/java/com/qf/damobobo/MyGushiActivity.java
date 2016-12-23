package com.qf.damobobo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.damobobo.adapter.MyGuShiAdapter;
import com.qf.damobobo.adapter.ShouYeAdapter;
import com.qf.damobobo.bean.MyGuSshiData;
import com.qf.damobobo.utils.SDUtil;
import com.qf.damobobo.utils.Shuju;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class MyGushiActivity extends AppCompatActivity implements PullToRefreshBase.OnRefreshListener2{

    private TextView tv;
    private PullToRefreshListView lv;
    int page=1;
    MyGuSshiData myGuSshiData=new MyGuSshiData();
    List<MyGuSshiData.DataBean>dataBeen=new ArrayList<>();
    MyGuShiAdapter adapter;
    private MyGuSshiData.UserBean user;
    EditText ed_qianmong;
    View view;
    private Uri photoUri;
    private String picPath;
    ImageView img_tou;
    private ImageView imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gushi);
        view = LayoutInflater.from(MyGushiActivity.this).inflate(R.layout.my_gushi_head, null);
        initViews();
        lv.setOnRefreshListener(this);
        addHear();
        lv.getRefreshableView().addHeaderView(view);
        adapter=new MyGuShiAdapter(dataBeen,MyGushiActivity.this);
        lv.setAdapter(adapter);
        lv.setRefreshing();

    }

    private void addHear() {

        TextView tv_nicheng= (TextView) view.findViewById(R.id.tv_nicheng);
        ImageView img_sex=(ImageView) view.findViewById(R.id.img_sex);
        img_tou=(ImageView) view.findViewById(R.id.img_tou);
        TextView tv_date=(TextView) view.findViewById(R.id.tv_date);
        ed_qianmong=(EditText) view.findViewById(R.id.ed_qianming);
        imgs = (ImageView) view.findViewById(R.id.imgs);

        if (!Shuju.sex.equals("")){
            if (Shuju.sex.equals("0")){
                img_sex.setImageResource(R.drawable.icon_man);
            }else {
                img_sex.setImageResource(R.drawable.icon_woman);
            }
        }else {
            img_sex.setImageResource(R.drawable.icon_portrait);
        }
        if (!Shuju.touXiang.equals("")){
            Picasso.with(MyGushiActivity.this).load(Shuju.TOUIMGLUJIN+Shuju.touXiang)
                    .into(img_tou);
        }else {
            img_tou.setImageResource(R.drawable.icon_mine);
        }
        if (!Shuju.qianMing.equals("")){
            ed_qianmong.setText(Shuju.qianMing);
        }
        if (!Shuju.niCheng.equals("")){
            tv_nicheng.setText(Shuju.niCheng);
        }
        if (!Shuju.shengRi.equals("")){
            String yyyy =Shuju.shengRi.substring(2, 3);
            tv_date.setText("我是"+yyyy+"0后");
        }

        img_tou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent, 0);
            }
        });
        ed_qianmong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_qianmong.setText("");
            }
        });
        imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s = ed_qianmong.getText().toString();
                OkHttpUtils.post()
                        .url(Shuju.ZHULUJIN+Shuju.GAI_QIAN_MING)
                        .addParams("uid",Shuju.userId)
                        .addParams("userpass",Shuju.Userpass)
                        .addParams("signature",s)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(MyGushiActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                if (response!=null){
                                    try {
                                        if (new JSONObject(response.trim().replace("\ufeff","")).optInt("result")==1){
                                            Toast.makeText(MyGushiActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
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
                                            editor.putString("birthday",Shuju.shengRi);
                                            editor.putString("id",Shuju.userId);
                                            editor.putString("name",Shuju.ZhangHao);
                                            editor.putString("pass",Shuju.Userpass);
                                            edit.putString("portrait",Shuju.touXiang);
                                            edit.putString("signature",s);
                                            editor.apply();

                                            Shuju.saveData(getSharedPreferences("sp",MODE_PRIVATE));
                                        }else {
                                            Toast.makeText(MyGushiActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
            }
        });
    }

    private void initViews() {
        tv = (TextView) findViewById(R.id.tv_hint);
        lv = (PullToRefreshListView) findViewById(R.id.recylerView_mygushi);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
    }

    public void myClick_bac(View view) {
        finish();
    }

    public void myClick(View view) {
        startActivity(new Intent(this,GushiActivity.class));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {

        page=1;
        initData(page);
    }

    private void initData(int page) {
        OkHttpUtils.post()
                .url(Shuju.ZHULUJIN+Shuju.WDGUSHI)
                .addParams("uid",Shuju.userId)
                .addParams("page",page+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MyGushiActivity.this, "网络不稳定", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response!=null){
                            try {
                                JSONObject object=new JSONObject(response.trim().replace("\ufeff",""));
                                if (object.optInt("result")==1){
                                    MyGuSshiData myGuSshiData = new Gson().fromJson(response.trim().replace("\ufeff", ""), MyGuSshiData.class);
                                    if (myGuSshiData.getData()!=null){
                                        dataBeen.addAll(myGuSshiData.getData());
                                    }
                                    user = myGuSshiData.getUser();
                                    adapter.notifyDataSetChanged();



                                }else {
                                    Toast.makeText(MyGushiActivity.this, object.optString("msg")+"", Toast.LENGTH_SHORT).show();
                                }
                                lv.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        lv.onRefreshComplete();
                                    }
                                },1000);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        initData(page);
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
        img_tou.setImageBitmap(bitmap);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (picPath!=null){
            String fileName = picPath.split("/")[picPath.length() - 1];
            OkHttpUtils.post()
                    .url(Shuju.ZHULUJIN+Shuju.GAI_TOU_XIANG)
                    .addParams("uid",Shuju.userId)
                    .addParams("userpass",Shuju.Userpass)
                    .addFile("portrai",fileName,new File(picPath))
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(MyGushiActivity.this, "网络等问题", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    if (response!=null){
                        try {
                            JSONObject object = new JSONObject(response.trim().replace("\ufeff", ""));
                            String s = object.optString("data");
                            if (object.optInt("result")==1){
                                Toast.makeText(MyGushiActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
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
                                editor.putString("birthday",Shuju.shengRi);
                                editor.putString("id",Shuju.userId);
                                editor.putString("name",Shuju.ZhangHao);
                                editor.putString("pass",Shuju.Userpass);
                                edit.putString("portrait",s );
                                edit.putString("signature",Shuju.qianMing);
                                editor.apply();

                                Shuju.saveData(getSharedPreferences("sp",MODE_PRIVATE));
                            }else {
                                Toast.makeText(MyGushiActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
