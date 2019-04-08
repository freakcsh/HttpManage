package com.freak.httphelper.download;

public interface HttpDownListener {
    /**
     * 开始下载
     */
    void downStart();

    /**
     * 下载暂停
     *
     * @param progress 下载进度
     */
    void downPause(long progress);

    /**
     * 下载停止
     */
    void downStop();

    /**
     * 下载完成
     *
     * @param httpDownInfo 下载文件实体类
     */
    void downFinish(HttpDownInfo httpDownInfo);

    /**
     * 下载出错
     *
     * @param httpDownInfo 下载文件实体类
     * @param msg          错误信息
     */
    void downError(HttpDownInfo httpDownInfo, String msg);

    /**
     * 下载进度
     *
     * @param readLength  已下载文件大小（当前下载进度）
     * @param countLength 文件总大小
     */
    void downProgress(long readLength, long countLength);
}
