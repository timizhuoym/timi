package com.qf.damobobo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qf.damobobo.R;
import com.qf.damobobo.bean.MyGuSshiData;
import com.qf.damobobo.utils.Shuju;
import com.qf.damobobo.views.MyTextView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by TimiZhuo on 2016/12/9.
 */
public class MyGuShiAdapter extends BaseAdapter {
    Context context;
    List<MyGuSshiData.DataBean>data;

    public MyGuShiAdapter(List<MyGuSshiData.DataBean>data, Context context) {
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
        TextView tv_date,tv_msg,tv_talk,tv_heart;
        GridView grid;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.my_gushi,null);
            viewHolder.grid= (GridView) convertView.findViewById(R.id.grid);
            viewHolder.tv_date= (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.tv_msg= (TextView) convertView.findViewById(R.id.tv_msg);
            viewHolder.tv_talk= (TextView) convertView.findViewById(R.id.tv_talk);
            viewHolder.tv_heart= (TextView) convertView.findViewById(R.id.tv_heart);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }




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
            viewHolder.grid.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
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
                convertView.setTag(views);
            }else {
                views= (Views) convertView.getTag();
            }
            Picasso.with(context).load(Shuju.TUPIAN+s.get(position)).into(views.img);
            return convertView;
        }
    }
}
