package com.freak.httpmanage.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.freak.httpmanage.R;
import com.freak.httpmanage.app.App;


/**
 * @author freak
 * @date 2019/02/29
 */
public class ToastUtil {

    public static boolean isShow = true;
    protected Context context;
    private Toast toast;
    private String message;
    private TextView mTextViewMessage = null;
    private static Toast mToast;
    private static long timeStamp = 0;
    private static final long TIME = 3000;
    private static ToastUtil sToastUtil;

    public static void show(int resId) {
        show(App.getInstance().getString(resId));
    }

    public static void show(String msg) {
        if (sToastUtil == null) {
            sToastUtil = new ToastUtil(App.getInstance());
        }
        sToastUtil.setText(msg);
        sToastUtil.create().show();
    }

    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    public void setText(String text) {
        message = text;
    }

    public Toast create() {

        if (toast == null) {
            View contentView = View.inflate(context, R.layout.view_dialog_toast, null);
            mTextViewMessage = (TextView) contentView.findViewById(R.id.tv_toast_msg);
            toast = new Toast(context);
            toast.setView(contentView);
            toast.setGravity(Gravity.TOP, 0, 800);
            toast.setDuration(Toast.LENGTH_LONG);
            mTextViewMessage.setText(message);
        } else {
            mTextViewMessage.setText(message);
        }

        return toast;
    }

    public static void shortShow(String msg) {
        if (sToastUtil == null) {
            sToastUtil = new ToastUtil(App.getInstance());
        }
        sToastUtil.setText(msg);
        sToastUtil.createShort().show();
    }

    /**
     * 不管触发多少次toast调用，只会持续一次toast显示时长
     *
     * @return
     */
    public Toast createShort() {

        if (toast == null) {
            View contentView = View.inflate(context, R.layout.view_dialog_toast, null);
            mTextViewMessage = (TextView) contentView.findViewById(R.id.tv_toast_msg);
            toast = new Toast(context);
            toast.setView(contentView);
            toast.setGravity(Gravity.TOP, 0, 800);
            toast.setDuration(Toast.LENGTH_SHORT);
            mTextViewMessage.setText(message);
        } else {
            mTextViewMessage.setText(message);
        }
        return toast;
    }

    public ToastUtil(Context context) {
        this.context = context;
    }


    public static void showLong(Context context, CharSequence messgae) {
        try {
            if (isShow) {
                toToast(context, messgae, Toast.LENGTH_SHORT);
            }
        } catch (Exception e) {

        }
    }


    public static void showShort(Context context, CharSequence message) {
        try {
            if (isShow) {
                toToast(context, message, Toast.LENGTH_SHORT);
            }
        } catch (Exception e) {

        }
    }


    public static void showNetErroMsg(Context context, Throwable e) {
        try {
            toToast(context, e.getMessage(), Toast.LENGTH_SHORT);
        } catch (Exception e1) {

        }
    }

    private static void toToast(Context context, CharSequence content, int time) {
        //3秒内不会重复弹出
        if (System.currentTimeMillis() - timeStamp > TIME) {
            mToast = null;
        }
        timeStamp = System.currentTimeMillis();
        if (mToast != null) {
            mToast.setText(content);
        } else {
            mToast = Toast.makeText(context, content, time);
        }
        mToast.show();
    }


}
