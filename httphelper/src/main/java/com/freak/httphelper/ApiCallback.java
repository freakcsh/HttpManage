package com.freak.httphelper;

/**
 * @author freak
 * @date 2019/01/25
 */
public interface ApiCallback<T> {
    /**
     * 请求成功回调
     *
     * @param model 实体
     */
    void onSuccess(T model);

    /**
     * 请求失败回调
     *
     * @param msg 错误信息/状态码
     */
    void onFailure(String msg);
}
