package com.freak.httpmanage.down;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.freak.httphelper.download.HttpDownInfo;
import com.freak.httphelper.download.HttpDownMethods;
import com.freak.httphelper.download.HttpDownStatus;
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
    private HttpDownMethods mHttpDownMethods;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, DownTaskListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_task_list);
        mHttpDownMethods = HttpDownMethods.getInstance();
        mHttpDownInfoList = new ArrayList<>();
        recycle_view = findViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        mDownTaskAdapter = new DownTaskAdapter(R.layout.item_download_task, mHttpDownInfoList, mHttpDownMethods);
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
        if (mHttpDownInfo.getState()== HttpDownStatus.WAITING){
            mHttpDownInfoList.add(mHttpDownInfo);

        }
        mDownTaskAdapter.notifyItemChanged(0);
    }

    public void download2(View view) {
        if (mHttpDownInfo2.getState()== HttpDownStatus.WAITING){
            mHttpDownInfoList.add(mHttpDownInfo2);

        }
        mDownTaskAdapter.notifyItemChanged(1);
    }

    public void download3(View view) {
        if (mHttpDownInfo3.getState()== HttpDownStatus.WAITING){
            mHttpDownInfoList.add(mHttpDownInfo3);
        }

        mDownTaskAdapter.notifyItemChanged(2);
    }

    public void download4(View view) {
        if (mHttpDownInfo4.getState()== HttpDownStatus.WAITING){
            mHttpDownInfoList.add(mHttpDownInfo4);
        }
        mDownTaskAdapter.notifyItemChanged(3);
    }

    public void download5(View view) {
        if (mHttpDownInfo5.getState()== HttpDownStatus.WAITING){
            mHttpDownInfoList.add(mHttpDownInfo5);
        }
        mDownTaskAdapter.notifyItemChanged(4);
    }

    public void downloadAll(View view) {
        mHttpDownInfoList.add(mHttpDownInfo);
        mHttpDownInfoList.add(mHttpDownInfo2);
        mHttpDownInfoList.add(mHttpDownInfo3);
        mHttpDownInfoList.add(mHttpDownInfo4);
        mHttpDownInfoList.add(mHttpDownInfo5);
        mDownTaskAdapter.notifyDataSetChanged();
    }

    public void cancelAll(View view) {
        mHttpDownMethods.downStopAll();
    }

    public void cancel5(View view) {
        mHttpDownMethods.downStop(mHttpDownInfo5);
    }

    public void cancel4(View view) {
        mHttpDownMethods.downStop(mHttpDownInfo4);
    }

    public void cancel3(View view) {
        mHttpDownMethods.downStop(mHttpDownInfo3);
    }

    public void cancel2(View view) {
        mHttpDownMethods.downStop(mHttpDownInfo2);
    }

    public void cancel1(View view) {
        mHttpDownMethods.downStop(mHttpDownInfo);
    }

    public void pauseAll(View view) {
        mHttpDownMethods.downPauseAll();
    }

    public void pause5(View view) {
        mHttpDownMethods.downPause(mHttpDownInfo5);
    }

    public void pause4(View view) {
        mHttpDownMethods.downPause(mHttpDownInfo4);
    }

    public void pause3(View view) {
        mHttpDownMethods.downPause(mHttpDownInfo3);
    }

    public void pause2(View view) {
        mHttpDownMethods.downPause(mHttpDownInfo2);
    }

    public void pause1(View view) {
        mHttpDownMethods.downPause(mHttpDownInfo);
    }
}
