package com.freak.httphelper;

import android.util.ArrayMap;

import java.util.Objects;
import java.util.Set;

import io.reactivex.disposables.Disposable;

public class RxApiManager implements RxActionManager<Object>{
    private static RxApiManager sInstance = null;

    private ArrayMap<Object, Disposable> maps;

    public static RxApiManager getInstance() {

        if (sInstance == null) {
            synchronized (RxApiManager.class) {
                if (sInstance == null) {
                    sInstance = new RxApiManager();
                }
            }
        }
        return sInstance;
    }

    private RxApiManager() {
        maps = new ArrayMap<>();
    }

    @Override
    public void add(Object tag, Disposable disposable) {
        maps.put(tag, disposable);

    }

    @Override
    public void remove(Object tag) {
        if (!maps.isEmpty()) {
            maps.remove(tag);
        }
    }

    public void removeAll() {
        if (!maps.isEmpty()) {
            maps.clear();
        }
    }

    @Override
    public void cancel(Object tag) {
        if (maps.isEmpty()) {
            return;
        }
        if (maps.get(tag) == null) {
            return;
        }
        if (!Objects.requireNonNull(maps.get(tag)).isDisposed()) {
            Objects.requireNonNull(maps.get(tag)).dispose();
            maps.remove(tag);
        }

    }

    @Override
    public void cancelAll() {
        if (maps.isEmpty()) {
            return;
        }
        Set<Object> keys = maps.keySet();
        for (Object apiKey : keys) {
            cancel(apiKey);
        }
    }
}
