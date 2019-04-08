package com.freak.httpmanage.down;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.freak.httpmanage.R;

public class SystemDownloadActivity extends AppCompatActivity {
    String wechatUrl = "http://dldir1.qq.com/weixin/android/weixin703android1400.apk";
    String qqUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    String jrtt = "http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk";
    String url = "http://pic2.zhimg.com/80/v2-4bd879d9876f90c1db0bd98ffdee17f0_hd.jpg";
    String url2 = "http://pic1.win4000.com/wallpaper/2017-10-11/59dde2bca944f.jpg";

    private DownloadManager downloadManager;
    private DownLoadCompleteReceiver receiver;
    private long id;

    public static void startAction(Context context) {
        Intent intent = new Intent(context, SystemDownloadActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_download);
        //获取系统服务
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        receiver = new DownLoadCompleteReceiver();
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);
    }

    public void downloadOrPauseAll(View view) {
    }

    public void cancelAll(View view) {
    }

    public void pauseAll(View view) {
    }

    public void downloadOrPause(View view) {
        switch (view.getId()) {
            case R.id.btn_download3:
                downLoadApk(wechatUrl, "QQ正在下载.....");
                break;
            case R.id.btn_download4:
                downLoadApk(qqUrl, "微信正在下载.....");
                break;
            default:
                break;
        }
    }

    public void cancel(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel3:
                downLoadApk(url, "微信正在下载.....");
                break;
            case R.id.btn_cancel4:
                downLoadApk(url2, "微信正在下载.....");
                break;
            default:
                break;
        }
    }

    private void downLoadApk(String url, String descriptionMessage) {
        //创建request对象
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置什么网络情况下可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        /**
         * Request.VISIBILITY_VISIBLE
         *在下载进行的过程中，通知栏中会一直显示该下载的Notification，当下载完成时，该Notification会被移除，这是默认的参数值。
         *
         * Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
         *在下载过程中通知栏会一直显示该下载的Notification，在下载完成后该Notification会继续显示，直到用户点击该Notification或者消除该Notification。
         *
         * Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION
         *只有在下载完成后该Notification才会被显示。
         *
         * Request.VISIBILITY_HIDDEN
         *不显示该下载请求的Notification。如果要使用这个参数，需要在应用的清单文件中加上DOWNLOAD_WITHOUT_NOTIFICATION权限。
         *
         *
         */
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //设置通知栏的标题
        request.setTitle("下载");
        //设置通知栏的message
        request.setDescription(descriptionMessage);
        //设置漫游状态下是否可以下载
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, getFileName(url));
        //进行下载
        id = downloadManager.enqueue(request);
    }

    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public void pause3(View view) {
    }

    public void pause4(View view) {
    }

    /**
     * 设置下载文件存储目录
     *
     * @param request
     */
    void setDownloadFilePath(DownloadManager.Request request) {
        /**
         * 方法1:
         * 目录: Android -> data -> com.app -> files -> Download -> 微信.apk
         * 这个文件是你的应用所专用的,软件卸载后，下载的文件将随着卸载全部被删除
         */

        //request.setDestinationInExternalFilesDir( this , Environment.DIRECTORY_DOWNLOADS ,  "微信.apk" );

        /**
         * 方法2:
         * 下载的文件存放地址  SD卡 download文件夹，pp.jpg
         * 软件卸载后，下载的文件会保留
         */
        //在SD卡上创建一个文件夹
        //request.setDestinationInExternalPublicDir(  "/mydownfile/"  , "weixin.apk" ) ;


        /**
         * 方法3:
         * 如果下载的文件希望被其他的应用共享
         * 特别是那些你下载下来希望被Media Scanner扫描到的文件（比如音乐文件）
         */
        //request.setDestinationInExternalPublicDir( Environment.DIRECTORY_MUSIC,  "笨小孩.mp3" );

        /**
         * 方法4
         * 文件将存放在外部存储的确实download文件内，如果无此文件夹，创建之，如果有，下面将返回false。
         * 系统有个下载文件夹，比如小米手机系统下载文件夹  SD卡--> Download文件夹
         */
        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        //设置文件存放路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "weixin.apk");
    }
}
