package com.freak.httphelper.rxview;

import android.annotation.SuppressLint;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

import static androidx.core.util.Preconditions.checkNotNull;
import static com.freak.httphelper.rxview.Preconditions.checkUiThread;


/**
 * @author Administrator
 * @date 2019/4/8
 */

public class RxView {
    public static int intervalTime;

    /**
     * 设置点击间隔时间
     *
     * @param intervalTime
     * @return
     */
    public static void setIntervalTime(int intervalTime) {
        RxView.intervalTime = intervalTime;
    }

    /**
     * 防止重复点击
     *
     * @param target 目标view
     * @param action 监听器
     */
    public static void setOnClickListeners(final OnRxViewClickListener<View> action, @NonNull View... target) {
        for (View view : target) {
            RxView.onClick(view).throttleFirst(intervalTime == 0 ? 1000 : intervalTime, TimeUnit.MILLISECONDS).subscribe(new Consumer<View>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull View view) throws Exception {
                    action.onRxViewClick(view);
                }
            });
        }
    }

    /**
     * 监听onclick事件防抖动
     *
     * @param view
     * @return
     */
    @SuppressLint("RestrictedApi")
    @CheckResult
    @NonNull
    private static Observable<View> onClick(@NonNull View view) {
        checkNotNull(view, "view == null");
        return Observable.create(new ViewClickOnSubscribe(view));
    }

    /**
     * onclick事件防抖动
     * 返回view
     */
    private static class ViewClickOnSubscribe implements ObservableOnSubscribe<View> {
        private View view;

        public ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(@io.reactivex.annotations.NonNull final ObservableEmitter<View> e) throws Exception {
            checkUiThread();

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!e.isDisposed()) {
                        e.onNext(view);
                    }
                }
            };
            view.setOnClickListener(listener);
        }
    }

    /**
     * A one-argument action. 点击事件转发接口
     *
     * @param <T> the first argument type
     */
    public interface OnRxViewClickListener<T> {
        /**
         * 点击事件
         *
         * @param view
         */
        void onRxViewClick(View view);
    }
}
