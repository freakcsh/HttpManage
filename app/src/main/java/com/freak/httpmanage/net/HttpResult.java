package com.freak.httpmanage.net;


import com.freak.httphelper.AbstractHttpResult;

/**
 * Created by Administrator on 2019/1/25.
 */

public class HttpResult<T> extends AbstractHttpResult<T> {
    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    protected T getResultData() {
        return getData();
    }

    @Override
    protected int getIntSuccessCode() {
        return 200;
    }

    @Override
    protected String getStringSuccessCode() {
        return null;
    }

    @Override
    protected String getResultErrorMsg() {
        return getMsg();
    }

    @Override
    protected int getIntResultCode() {
        return getCode();
    }

    @Override
    protected String getStringResultCode() {
        return null;
    }

    @Override
    protected int[] getIntOtherCode() {
        return new int[]{0, 46};
    }

    @Override
    protected String[] getStringOtherCode() {
        return new String[0];
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (null != data) {
            sb.append(data.toString());
        }
        return sb.toString();
    }
}
