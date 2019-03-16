package com.freak.httpmanage.net.log;

import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 *
 * @author freak
 * @date 2019/03/12
 * Log日志工具，封装logger
 */
public class LogUtil {
    /**
     * 初始化log工具，在app入口处调用
     *
     * @param logName 打印日志名字
     * @param isLog 是否打印log
     */
    public static void init(String  logName, final boolean isLog) {
        FormatStrategy mFormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // （可选）是否显示线程信息。默认值true
//                .methodCount(5)         // （可选）要显示的方法行数。默认值2
//                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。默认值5
//                .logStrategy() // （可选）更改要打印的日志策略。默认LogCat
                .tag(logName)   // （可选）每个日志的全局标记。默认PRETTY_LOGGER .build
                .build();
        //log日志打印框架Logger
        com.orhanobut.logger.Logger.addLogAdapter(new AndroidLogAdapter(mFormatStrategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isLog;
            }
        });
    }

    public static void d(String message) {
        Logger.d(message);
    }
    public static void e(String message) {
        Logger.e(message);
    }

    public static void i(String message) {
        Logger.i(message);
    }

    public static void w(String message, Throwable e) {
        String info = e != null ? e.toString() : "null";
        Logger.w(message + "：" + info);
    }

    public static void e(String message, Throwable e) {
        Logger.e(e, message);
    }

    public static void json(String json) {
        Logger.json(json);
    }
}
