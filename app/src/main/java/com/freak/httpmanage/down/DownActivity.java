package com.freak.httpmanage.down;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.freak.httphelper.down.HttpDownInfo;
import com.freak.httphelper.down.HttpDownListener;
import com.freak.httphelper.down.HttpDownMethods;
import com.freak.httpmanage.R;
import com.freak.httpmanage.net.log.LogUtil;

public class DownActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 001;
    TextView tv_file_name1, tv_progress1, tv_file_name2, tv_progress2;
    Button btn_download1, btn_download2, btn_download_all;
    ProgressBar pb_progress1, pb_progress2;
    HttpDownMethods mHttpDownMethods;
    String wechatUrl = "http://dldir1.qq.com/weixin/android/weixin703android1400.apk";
    String qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    private HttpDownInfo mDownInfo;
    private HttpDownInfo mDownInfo2;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, DownActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        initViews();
        initDownloads();
    }

    private void initDownloads() {
        mHttpDownMethods = HttpDownMethods.getInstance();
        mDownInfo = new HttpDownInfo();
        mDownInfo.setUrl(qqUrl);
        mDownInfo.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/rro/test1.apk");
        mDownInfo2 = new HttpDownInfo();
        mDownInfo2.setUrl(wechatUrl);
        mDownInfo2.setSavePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/rro/test2.apk");
    }

    /**
     * 初始化View控件
     */
    private void initViews() {
        tv_file_name1 = findViewById(R.id.tv_file_name1);
        tv_progress1 = findViewById(R.id.tv_progress1);
        pb_progress1 = findViewById(R.id.pb_progress1);
        btn_download1 = findViewById(R.id.btn_download1);
        tv_file_name1.setText("微信");

        tv_file_name2 = findViewById(R.id.tv_file_name2);
        tv_progress2 = findViewById(R.id.tv_progress2);
        pb_progress2 = findViewById(R.id.pb_progress2);
        btn_download2 = findViewById(R.id.btn_download2);
        tv_file_name2.setText("qq");

        btn_download_all = findViewById(R.id.btn_download_all);

    }

    /**
     * 下载或暂停下载
     *
     * @param view
     */
    public void downloadOrPause(View view) {
        switch (view.getId()) {
            case R.id.btn_download1:

                mHttpDownMethods.downStart(mDownInfo, new HttpDownListener() {
                    @Override
                    public void downStart() {
                        btn_download1.setText("暂停");
                    }

                    @Override
                    public void downPause(long progress) {
                        Toast.makeText(DownActivity.this, "暂停了!", Toast.LENGTH_SHORT).show();
                        LogUtil.e("暂停了");
                        btn_download1.setText("下载");
                    }

                    @Override
                    public void downStop(long progress) {
                        Toast.makeText(DownActivity.this, "停止了!", Toast.LENGTH_SHORT).show();
                        LogUtil.e("停止了");
                        btn_download1.setText("下载停止了");
                    }

                    @Override
                    public void downFinish(HttpDownInfo httpDownInfo) {
                        Toast.makeText(DownActivity.this, "下载完成!", Toast.LENGTH_SHORT).show();
                        LogUtil.e("下载完成");
                        btn_download1.setText("下载完成");
                    }

                    @Override
                    public void downError(HttpDownInfo httpDownInfo, String msg) {
                        Toast.makeText(DownActivity.this, "出错了!", Toast.LENGTH_SHORT).show();
                        LogUtil.e("出错了");
                        btn_download1.setText("下载出错了");
                    }

                    @Override
                    public void downProgress(long readLength, long countLength) {
                        int pro = (int) (readLength * 100 / countLength);
                        pb_progress1.setProgress(pro);
                        tv_progress1.setText(pro + "%");
                    }
                });
                break;
            case R.id.btn_download2:
                mHttpDownMethods.downStart(mDownInfo2, new HttpDownListener() {
                    @Override
                    public void downStart() {
                        btn_download2.setText("暂停");
                    }

                    @Override
                    public void downPause(long progress) {
                        Toast.makeText(DownActivity.this, "暂停了!", Toast.LENGTH_SHORT).show();
                        LogUtil.e("暂停了");
                        btn_download2.setText("下载");
                    }

                    @Override
                    public void downStop(long progress) {
                        Toast.makeText(DownActivity.this, "停止了!", Toast.LENGTH_SHORT).show();
                        LogUtil.e("停止了");
                        btn_download2.setText("下载停止了");
                    }

                    @Override
                    public void downFinish(HttpDownInfo httpDownInfo) {
                        Toast.makeText(DownActivity.this, "下载完成!", Toast.LENGTH_SHORT).show();
                        LogUtil.e("下载完成");
                        btn_download2.setText("下载完成");
                    }

                    @Override
                    public void downError(HttpDownInfo httpDownInfo, String msg) {
                        Toast.makeText(DownActivity.this, "出错了!", Toast.LENGTH_SHORT).show();
                        LogUtil.e("出错了"+msg);
                        btn_download2.setText("下载出错了");
                    }

                    @Override
                    public void downProgress(long readLength, long countLength) {
                        int pro = (int) (readLength * 100 / countLength);
                        pb_progress2.setProgress(pro);
                        tv_progress2.setText(pro + "%");
                    }
                });
                break;
            default:

                break;
        }
    }

    public void downloadOrPauseAll(View view) {
//        if (!mDownloadManager.isDownloading(wechatUrl, qqUrl)) {
//            btn_download1.setText("暂停");
//            btn_download2.setText("暂停");
//            btn_download_all.setText("全部暂停");
//            mDownloadManager.download(wechatUrl, qqUrl);//最好传入个String[]数组进去
//        } else {
//            mDownloadManager.pause(wechatUrl, qqUrl);
//            btn_download1.setText("下载");
//            btn_download2.setText("下载");
//            btn_download_all.setText("全部下载");
//        }
    }

    /**
     * 取消下载
     *
     * @param view
     */
    public void cancel(View view) {

        switch (view.getId()) {
            case R.id.btn_cancel1:
                mHttpDownMethods.downStop(mDownInfo);
                break;
            case R.id.btn_cancel2:
                mHttpDownMethods.downStop(mDownInfo2);
                break;
            default:
                break;
        }
    }

    public void cancelAll(View view) {
//        mHttpDownMethods.cancel(wechatUrl, qqUrl);
        btn_download1.setText("下载");
        btn_download2.setText("下载");
        btn_download_all.setText("全部下载");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if (!checkPermission(permission)) {//针对android6.0动态检测申请权限
            if (shouldShowRationale(permission)) {
                showMessage("需要权限跑demo哦...");
            }
            ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        cancelAll(null);
    }

    /**
     * 显示提示消息
     *
     * @param msg
     */
    private void showMessage(String msg) {
        Toast.makeText(DownActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测用户权限
     *
     * @param permission
     * @return
     */
    protected boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 是否需要显示请求权限的理由
     *
     * @param permission
     * @return
     */
    protected boolean shouldShowRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
    }
}
