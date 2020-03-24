package com.freak.httphelper.lifecycle;

import com.freak.httphelper.mvp.IDaggerView;
import com.freak.httphelper.rxview.Preconditions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.annotations.NonNull;

/**
 * 使用此类操作 RxLifecycle 的特性
 */
public class RxLifecycleUtils {
    private RxLifecycleUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 绑定 Activity 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IDaggerView view,
                                                             final ActivityEvent event) {
        Preconditions.checkNotNull(view, "view == null");
        if (view instanceof ActivityLifecycleAble) {
            return bindUntilEvent((ActivityLifecycleAble) view, event);
        } else {
            throw new IllegalArgumentException("view isn't ActivityLifecycleAble");
        }
    }

    /**
     * 绑定 Fragment 的指定生命周期
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull final IDaggerView view,
                                                             final FragmentEvent event) {
        Preconditions.checkNotNull(view, "view == null");
        if (view instanceof FragmentLifecycleAble) {
            return bindUntilEvent((FragmentLifecycleAble) view, event);
        } else {
            throw new IllegalArgumentException("view isn't FragmentLifecycleAble");
        }
    }

    public static <T, R> LifecycleTransformer<T> bindUntilEvent(@NonNull final LifecycleAble<R> lifecycleAble,
                                                                final R event) {
        Preconditions.checkNotNull(lifecycleAble, "lifecycleAble == null");
        return RxLifecycle.bindUntilEvent(lifecycleAble.provideLifecycleSubject(), event);
    }

    /**
     * 绑定 Activity/Fragment 的生命周期
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IDaggerView view) {
        Preconditions.checkNotNull(view, "view == null");
        if (view instanceof LifecycleAble) {
            return bindToLifecycle((LifecycleAble) view);
        } else {
            throw new IllegalArgumentException("view isn't LifecycleAble");
        }
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull LifecycleAble lifecycleAble) {
        Preconditions.checkNotNull(lifecycleAble, "lifecycleAble == null");
        if (lifecycleAble instanceof ActivityLifecycleAble) {
            return RxLifecycleAndroid.bindActivity(((ActivityLifecycleAble) lifecycleAble).provideLifecycleSubject());
        } else if (lifecycleAble instanceof FragmentLifecycleAble) {
            return RxLifecycleAndroid.bindFragment(((FragmentLifecycleAble) lifecycleAble).provideLifecycleSubject());
        } else {
            throw new IllegalArgumentException("LifecycleAble not match");
        }
    }
}
