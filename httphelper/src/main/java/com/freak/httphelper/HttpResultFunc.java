package com.freak.httphelper;

import android.text.TextUtils;

import rx.functions.Func1;

/**
 * 此方法是接口返回数据的解析
 *
 * @param <T>
 * @author freak
 * @date 2019/01/25
 */
public class HttpResultFunc<T> implements Func1<AbstractHttpResult<T>, T> {
    @Override
    public T call(AbstractHttpResult<T> tHttpResult) {
        //int类型结果码
        if (TextUtils.isEmpty(tHttpResult.getStringSuccessCode())) {
            if (tHttpResult.getIntResultCode() != tHttpResult.getIntSuccessCode()) {
                int[] otherCode = tHttpResult.getIntOtherCode();
                for (int code : otherCode) {
                    if (tHttpResult.getIntResultCode() == code) {
                        throw new ApiException(tHttpResult.getIntResultCode() + "");
                    }
                }
                throw new ApiException(tHttpResult.getResultErrorMsg());
            }
            return tHttpResult.getResultData();
        } else {
            //string类型结果码
            if (!tHttpResult.getStringSuccessCode().equals(tHttpResult.getStringResultCode())) {
                String[] otherCode = tHttpResult.getStringOtherCode();
                for (String code : otherCode) {
                    if (tHttpResult.getStringResultCode().equals(code)) {
                        throw new ApiException(tHttpResult.getStringResultCode());
                    }
                }
                throw new ApiException(tHttpResult.getResultErrorMsg());
            }
            return tHttpResult.getResultData();
        }
    }
}
