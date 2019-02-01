package com.freak.httphelper;

/**
 * 自定义api异常
 * @author freak
 * @date 2019/01/25
 */
public class ApiException extends RuntimeException {
    public ApiException(String detailMessage) {
        super(detailMessage);
    }
}
