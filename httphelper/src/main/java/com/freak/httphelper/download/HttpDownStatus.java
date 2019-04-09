package com.freak.httphelper.download;

/**
 * 下载状态
 *
 * @author Administrator
 */
public class HttpDownStatus {
    /**
     * START    正在下载（开始下载）
     * PAUSE    暂停下载
     * STOP     停止下载（取消下载）
     * FINISH   下载完成
     * ERROR    下载错误
     * WAITING  等待下载
     */
    public static final int START = 1;
    public static final int PAUSE = 2;
    public static final int STOP = 3;
    public static final int FINISH = 4;
    public static final int ERROR = 5;
    public static final int WAITING = 6;
}
