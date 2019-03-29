package com.freak.httphelper.down;

public interface HttpDownListener {
    void downStart();

    void downPause(long progress);

    void downStop(long progress);

    void downFinish(HttpDownInfo httpDownInfo);

    void downError(HttpDownInfo httpDownInfo,String msg);

    void downProgress(long readLength, long countLength);
}
