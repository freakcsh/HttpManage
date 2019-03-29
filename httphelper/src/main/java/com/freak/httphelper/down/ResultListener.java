package com.freak.httphelper.down;

public interface ResultListener {
    void downFinish(HttpDownInfo httpDownInfo);

    void downError(HttpDownInfo httpDownInfo);
}
