package com.freak.httphelper.down;

public class HttpDownInfo {
    /**
     * id                   下载id
     * readLength           完成大小(当前下载大小)
     * countLength          总大小
     * url                  下载地址
     * savePath             保存路径
     * listener             下载监听
     * state                下载状态
     * isStartMoreThread    是否开启多线程下载
     * downThreadCount      下载线程数
     */
    private long id;
    private long readLength;
    private long countLength;
    private String url;
    private String savePath;
    private HttpDownListener listener;
    private int state;
    private boolean isStartMoreThread = false;
    private int downThreadCount = 3;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReadLength() {
        return this.readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public long getCountLength() {
        return this.countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public HttpDownListener getListener() {
        return this.listener;
    }

    public void setListener(HttpDownListener listener) {
        this.listener = listener;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isStartMoreThread() {
        return this.isStartMoreThread;
    }

    public void setStartMoreThread(boolean startMoreThread) {
        this.isStartMoreThread = startMoreThread;
    }

    public int getDownThreadCount() {
        return this.downThreadCount;
    }

    public void setDownThreadCount(int downThreadCount) {
        this.downThreadCount = downThreadCount;
    }

    @Override
    public String toString() {
        return "HttpDownInfo{" +
                "id=" + id +
                ", readLength=" + readLength +
                ", countLength=" + countLength +
                ", url='" + url + '\'' +
                ", savePath='" + savePath + '\'' +
                ", listener=" + listener +
                ", state=" + state +
                ", isStartMoreThread=" + isStartMoreThread +
                ", downThreadCount=" + downThreadCount +
                '}';
    }
}
