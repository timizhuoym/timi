package com.qf.damobobo.adapter;

import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.ChineseCalendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qf.damobobo.R;
import com.qf.damobobo.bean.LeftData;
import com.qf.damobobo.utils.PxUtiles;
import com.qf.damobobo.utils.Shuju;
import com.qf.damobobo.views.MyTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by TimiZhuo on 2016/12/7.
 */
public class ShouYeAdapter extends BaseAdapter {
    Context context;
    List<LeftData.DataBean>data;

    public ShouYeAdapter(List<LeftData.DataBean>data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data==null?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        TextView tv_city,tv_date,tv_msg,tv_talk,tv_heart;
        GridView grid;
        ImageView img,sex_img;
        MyTextView tv_top;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.shouye_left_layout,null);
            viewHolder.grid= (GridView) convertView.findViewById(R.id.grid);
            viewHolder.img= (ImageView) convertView.findViewById(R.id.img);
            viewHolder.sex_img= (ImageView) convertView.findViewById(R.id.sex_img);
            viewHolder.tv_top= (MyTextView) convertView.findViewById(R.id.tv_top);
            viewHolder.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_msg= (TextView) convertView.findViewById(R.id.tv_msg);
            viewHolder.tv_talk= (TextView) convertView.findViewById(R.id.tv_talk);
            viewHolder.tv_heart= (TextView) convertView.findViewById(R.id.tv_heart);
            viewHolder.tv_city= (TextView) convertView.findViewById(R.id.tv_city);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        String city= (String) data.get(position).getCity();
        if (city==null||city.equals("")){
            viewHolder.tv_city.setText("未知城市");
        }else {
            viewHolder.tv_city.setText(city);
        }

        String imgPath= (String) data.get(position).getUser().getPortrait();
        if (imgPath==null||imgPath.equals("")){
            viewHolder.img.setImageResource(R.drawable.icon_portrait);
        }else {
            Picasso.with(context).load(Shuju.TOUIMGLUJIN+imgPath).into(viewHolder.img);
        }

        String sex= (String) data.get(position).getUser().getUsersex();
        if (sex==null||sex.equals("")){
            viewHolder.sex_img.setImageResource(R.drawable.icon_mine);
        }else {
            if (sex.equals("0")){
                viewHolder.sex_img.setImageResource(R.drawable.icon_man);
            }else {
                viewHolder.sex_img.setImageResource(R.drawable.icon_woman);
            }
        }

        viewHolder.tv_top.setText(data.get(position).getUser().getNickname());

        viewHolder.tv_msg.setText(data.get(position).getStory_info());

        List<String> pics=  data.get(position).getPics();
        if (pics!=null){
            if (pics.size()==1){
                viewHolder.grid.setNumColumns(1);
            }else if (pics.size()==2){
                viewHolder.grid.setNumColumns(2);
            }else {
                viewHolder.grid.setNumColumns(3);
            }
            viewHolder.grid.setAdapter(new MyAdapter(pics));
        }

        viewHolder.tv_talk.setText(data.get(position).getReadcount());

        viewHolder.tv_heart.setText(data.get(position).getComment());
        long time=(Long.parseLong(data.get(position).getStory_time())*1000);
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
        viewHolder.tv_date.setText(s);

        return convertView;
    }


    class MyAdapter extends BaseAdapter{
        List<String>s=new ArrayList<>();
        public MyAdapter(List<String> s){
            this.s=s;
        }

        @Override
        public int getCount() {
            return s.size();
        }

        @Override
        public Object getItem(int position) {
            return s.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        class Views{
            ImageView img;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Views views=null;
            if (convertView==null){
                views=new Views();
                convertView=LayoutInflater.from(context).inflate(R.layout.grid_item,null);
                views.img= (ImageView) convertView.findViewById(R.id.img);
                LinearLayout.LayoutParams params = null;
                if (s.size()==1){
                    float v = PxUtiles.dpToPx(300, context);
                    params=new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) v
                    );
                }else if (s.size()==2){
                    float v = PxUtiles.dpToPx(200, context);
                    params=new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) v
                    );

                }else {
                    float v = PxUtiles.dpToPx(100, context);
                    params=new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            (int) v
                    );
                }
                views.img.setPadding(((int) PxUtiles.dpToPx(2, context)),0,((int) PxUtiles.dpToPx(2, context)),0);
                views.img.setLayoutParams(params);
                convertView.setTag(views);
            }else {
                views= (Views) convertView.getTag();
            }
            Picasso.with(context).load(Shuju.TUPIAN+s.get(position)).into(views.img);
            return convertView;
        }
    }
}
