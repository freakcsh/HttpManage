package com.freak.httphelper.download;

public interface ProgressListener {
    /**
     * 下载进度监听
     *
     * @param bytesRead     下载好的数据
     * @param contentLength 总下载大小
     * @param done          是否完成
     */
    void onProgress(long bytesRead, long contentLength, boolean done);
}
