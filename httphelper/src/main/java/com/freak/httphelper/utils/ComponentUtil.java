package com.freak.httphelper.utils;

import android.content.Context;

import com.freak.httphelper.dagger.component.AppComponent;
import com.freak.httphelper.delegate.App;
import com.freak.httphelper.rxview.Preconditions;

public class ComponentUtil {
    public static AppComponent obtainAppComponentFromContext(Context context) {
        Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
        Preconditions.checkState(context.getApplicationContext() instanceof App, "%s must be implements %s", context.getApplicationContext().getClass().getName(), App.class.getName());
        return ((App) context.getApplicationContext()).getAppComponent();
    }
}
