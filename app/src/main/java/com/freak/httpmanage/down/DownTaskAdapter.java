package com.freak.httpmanage.down;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.freak.httphelper.download.HttpDownInfo;
import com.freak.httphelper.download.HttpDownListener;
import com.freak.httphelper.download.HttpDownMethods;
import com.freak.httphelper.download.HttpDownStatus;
import com.freak.httpmanage.R;
import com.freak.httpmanage.net.log.LogUtil;

import java.util.List;

public class DownTaskAdapter extends BaseQuickAdapter<HttpDownInfo, BaseViewHolder> {
    private HttpDownMethods mHttpDownMethods;

    public DownTaskAdapter(int layoutResId, @Nullable List<HttpDownInfo> data, HttpDownMethods httpDownMethods) {
        super(layoutResId, data);
        this.mHttpDownMethods = httpDownMethods;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HttpDownInfo item) {
        mHttpDownMethods.setHttpDownListener(new HttpDownListener() {
            @Override
            public void downStart() {
                helper.setText(R.id.btn_download_task, "暂停");
                helper.setText(R.id.tv_file_name_task, "下载中：");
            }

            @Override
            public void downPause(long progress) {
                LogUtil.e("暂停了" + helper.getAdapterPosition());
                item.setReadLength(progress);
                item.setState(HttpDownStatus.PAUSE);
                helper.setText(R.id.btn_download_task, "下载");
                helper.setText(R.id.tv_file_name_task, "下载暂停：");
            }

            @Override
            public void downStop() {
                LogUtil.e("停止了" + helper.getAdapterPosition());
                remove(helper.getAdapterPosition());
                notifyDataSetChanged();
            }

            @Override
            public void downFinish(HttpDownInfo httpDownInfo) {
                LogUtil.e("下载完成" + helper.getAdapterPosition() + "\n下载完成地址：" + httpDownInfo.getUrl());
                remove(helper.getAdapterPosition());
                notifyDataSetChanged();
//                item.setReadLength(httpDownInfo.getReadLength());
//                item.setCountLength(httpDownInfo.getCountLength());
//                item.setState(HttpDownStatus.FINISH);
//                helper.setText(R.id.btn_download_task, "下载完成");
//                helper.setText(R.id.tv_file_name_task, "下载完成：");
//                int pro = (int) (httpDownInfo.getReadLength() * 100 / httpDownInfo.getCountLength());
//                helper.setProgress(R.id.pb_progress_task, pro);
//                helper.setText(R.id.tv_progress_task, pro + "%");
            }

            @Override
            public void downError(HttpDownInfo httpDownInfo, String msg) {
                LogUtil.e("出错了" + helper.getAdapterPosition() + "\n下载完成地址：" + httpDownInfo.getUrl());
                item.setReadLength(httpDownInfo.getReadLength());
                item.setCountLength(httpDownInfo.getCountLength());
                item.setState(HttpDownStatus.ERROR);
                helper.setText(R.id.btn_download_task, "下载出错了");
                helper.setText(R.id.tv_file_name_task, "下载出错了：");
                int pro = (int) (httpDownInfo.getReadLength() * 100 / httpDownInfo.getCountLength());
                helper.setProgress(R.id.pb_progress_task, pro);
                helper.setText(R.id.tv_progress_task, pro + "%");
            }

            @Override
            public void downProgress(long readLength, long countLength) {
                int pro = (int) (readLength * 100 / countLength);
                helper.setProgress(R.id.pb_progress_task, pro);
                helper.setText(R.id.tv_progress_task, pro + "%");
            }
        });
        mHttpDownMethods.addDownTask(item);
    }
}
