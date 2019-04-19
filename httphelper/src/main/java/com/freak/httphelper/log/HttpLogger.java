package com.freak.httphelper.log;

import com.freak.httphelper.HttpMethods;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author freak
 * @date 2019/3/11
 */

public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
            if (message.endsWith("http/1.1")) {
                String url = message.substring(message.indexOf("http"), message.lastIndexOf("http/1.1"));
                mMessage.append("请求地址--> " + UrlUtil.getURLDecoderString(url).concat("\n"));
            }
        }
        /**
         * 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
         */
        if ((message.startsWith("{") && message.endsWith("}"))
                || (message.startsWith("[") && message.endsWith("]"))) {
            message = JsonUtil.formatJson(message);
        }
        mMessage.append(message.concat("\n"));
        /**
         * 请求或者响应结束，打印整条日志
         */
        if (message.startsWith("<-- END HTTP")) {
            if (HttpMethods.getInstanceBuilder().getLogLevel() == 0) {
                LogUtil.d(mMessage.toString());
            } else {
                switch (HttpMethods.getInstanceBuilder().getLogLevel()) {
                    case LogLevel.ERROR:
                        LogUtil.e(mMessage.toString());
                        break;
                    case LogLevel.DEBUG:
                        LogUtil.d(mMessage.toString());
                        break;
                    default:
                        LogUtil.d(mMessage.toString());
                        break;

                }
            }


        }
    }

}
