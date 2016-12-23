package com.qf.damobobo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.qf.damobobo.R;
import com.qf.damobobo.adapter.ShouYeAdapter;
import com.qf.damobobo.bean.LeftData;
import com.qf.damobobo.utils.Shuju;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by TimiZhuo on 2016/12/5.
 */
public class LeftFragment extends Fragment{

    private View view;
    private PullToRefreshListView lv;
    ShouYeAdapter adapter;
    int page=1;
    List<LeftData.DataBean>data=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.left_fragment_layout, null);
        lv = (PullToRefreshListView) view.findViewById(R.id.recylerView_left);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                data.clear();
                page=1;
                downloadData(page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                page++;
                downloadData(page);
            }
        });
        adapter=new ShouYeAdapter(data,getContext());
        lv.setAdapter(adapter);
        lv.setRefreshing();
        return view;
    }

    private void downloadData(int a) {
        OkHttpUtils.post()
                .url(Shuju.ZHULUJIN+Shuju.GETSTORY)
                .addParams("type","new")
                .addParams("page",a+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if (response!=null){
                            String s=response.trim().replace("\ufeff","");
                            try {
                                JSONObject object=new JSONObject(s);
                                int a=object.optInt("result");
                                if (a==1){
                                    Gson gson=new Gson();
                                    LeftData listViewItem = gson.fromJson(response.trim().replace("\ufeff",""), LeftData.class);
                                    data.addAll(listViewItem.getData());
                                    adapter.notifyDataSetChanged();

                                }else {
                                    Toast.makeText(LeftFragment.this.getContext(),"网络等异常",Toast.LENGTH_SHORT).show();
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


}
