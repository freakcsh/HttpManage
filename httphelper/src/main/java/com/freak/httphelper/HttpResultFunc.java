package com.freak.httphelper;

import android.text.TextUtils;

import io.reactivex.functions.Function;

/**
 * 此方法是接口返回数据的解析
 *
 * @param <T>
 * @author freak
 * @date 2019/01/25
 */
public class HttpResultFunc<T> implements Function<AbstractHttpResult<T>, T> {

    @Override
    public T apply(AbstractHttpResult<T> tAbstractHttpResult) throws Exception {
        //int类型结果码
        if (TextUtils.isEmpty(tAbstractHttpResult.getStringSuccessCode())) {
            if (tAbstractHttpResult.getIntResultCode() != tAbstractHttpResult.getIntSuccessCode()) {
                int[] otherCode = tAbstractHttpResult.getIntOtherCode();
                for (int code : otherCode) {
                    if (tAbstractHttpResult.getIntResultCode() == code) {
                        throw new ApiException(tAbstractHttpResult.getIntResultCode() + "");
                    }
                }
                throw new ApiException(tAbstractHttpResult.getResultErrorMsg());
            }
            return tAbstractHttpResult.getResultData();
        } else {
            //string类型结果码
            if (!tAbstractHttpResult.getStringSuccessCode().equals(tAbstractHttpResult.getStringResultCode())) {
                String[] otherCode = tAbstractHttpResult.getStringOtherCode();
                for (String code : otherCode) {
                    if (tAbstractHttpResult.getStringResultCode().equals(code)) {
                        throw new ApiException(tAbstractHttpResult.getStringResultCode());
                    }
                }
                throw new ApiException(tAbstractHttpResult.getResultErrorMsg());
            }
            return tAbstractHttpResult.getResultData();
        }
    }
}
