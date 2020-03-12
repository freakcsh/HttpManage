package com.freak.httphelper;

import io.reactivex.disposables.Disposable;

public interface RxActionManager<T> {

    void add(T tag, Disposable disposable);
    void remove(T tag);
    void cancel(T tag);
    void cancelAll();
}
