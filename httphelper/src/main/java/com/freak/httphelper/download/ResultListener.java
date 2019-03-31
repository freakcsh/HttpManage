package com.freak.httphelper.download;

public interface ResultListener {
    void downFinish(HttpDownInfo httpDownInfo);

    void downError(HttpDownInfo httpDownInfo);
}
