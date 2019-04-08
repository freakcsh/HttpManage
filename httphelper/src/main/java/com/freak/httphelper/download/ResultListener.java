package com.freak.httphelper.download;

public interface ResultListener {
    /**
     * 下载结束
     *
     * @param httpDownInfo
     */
    void downFinish(HttpDownInfo httpDownInfo);

    /**
     * 下载错误
     *
     * @param httpDownInfo
     */
    void downError(HttpDownInfo httpDownInfo);
}
