package com.freak.httpmanage.down;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.freak.httphelper.download.HttpDownInfo;
import com.freak.httphelper.download.HttpDownMethods;
import com.freak.httpmanage.MainActivity;
import com.freak.httpmanage.R;

import java.util.ArrayList;
import java.util.List;

public class DownTaskListActivity extends AppCompatActivity {
    String wechatUrl = "http://dldir1.qq.com/weixin/android/weixin703android1400.apk";
    String qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    String jrtt = "http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk";
    String url = "http://pic2.zhimg.com/80/v2-4bd879d9876f90c1db0bd98ffdee17f0_hd.jpg";
    String url2 = "http://pic1.win4000.com/wallpaper/2017-10-11/59dde2bca944f.jpg";
    private RecyclerView recycle_view;
    private DownTaskAdapter mDownTaskAdapter;
    private List<HttpDownInfo> mHttpDownInfoList;
    private HttpDownInfo mHttpDownInfo;
    private HttpDownInfo mHttpDownInfo2;
    private HttpDownInfo mHttpDownInfo3;
    private HttpDownInfo mHttpDownInfo4;
    private HttpDownInfo mHttpDownInfo5;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, DownTaskListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_task_list);
        mHttpDownInfoList = new ArrayList<>();
        recycle_view = findViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        mDownTaskAdapter = new DownTaskAdapter(R.layout.item_download_task, mHttpDownInfoList, HttpDownMethods.getInstance());
        mDownTaskAdapter.bindToRecyclerView(recycle_view);
        mDownTaskAdapter.setEmptyView(R.layout.item_no_date, recycle_view);
        mHttpDownInfo = new HttpDownInfo();
        mHttpDownInfo.setUrl(wechatUrl);

        mHttpDownInfo2 = new HttpDownInfo();
        mHttpDownInfo2.setUrl(qqUrl);

        mHttpDownInfo3 = new HttpDownInfo();
        mHttpDownInfo3.setUrl(jrtt);

        mHttpDownInfo4 = new HttpDownInfo();
        mHttpDownInfo4.setUrl(url);

        mHttpDownInfo5 = new HttpDownInfo();
        mHttpDownInfo5.setUrl(url2);
    }

    public void download1(View view) {
        mHttpDownInfoList.add(mHttpDownInfo);
        mDownTaskAdapter.notifyDataSetChanged();
    }

    public void download2(View view) {
        mHttpDownInfoList.add(mHttpDownInfo2);
        mDownTaskAdapter.notifyDataSetChanged();
    }

    public void download3(View view) {
        mHttpDownInfoList.add(mHttpDownInfo3);
        mDownTaskAdapter.notifyDataSetChanged();
    }

    public void download4(View view) {
        mHttpDownInfoList.add(mHttpDownInfo4);
        mDownTaskAdapter.notifyDataSetChanged();
    }

    public void download5(View view) {
        mHttpDownInfoList.add(mHttpDownInfo5);
        mDownTaskAdapter.notifyDataSetChanged();
    }

    public void downloadAll(View view) {
        mHttpDownInfoList.add(mHttpDownInfo);
        mHttpDownInfoList.add(mHttpDownInfo2);
        mHttpDownInfoList.add(mHttpDownInfo3);
        mHttpDownInfoList.add(mHttpDownInfo4);
        mHttpDownInfoList.add(mHttpDownInfo5);
        mDownTaskAdapter.notifyDataSetChanged();
    }
}
