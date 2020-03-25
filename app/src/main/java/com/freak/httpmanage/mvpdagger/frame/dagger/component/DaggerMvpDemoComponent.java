package com.freak.httpmanage.mvpdagger.frame.dagger.component;

import com.freak.httphelper.dagger.component.AppComponent;
import com.freak.httphelper.dagger.scope.ActivityScope;
import com.freak.httpmanage.mvpdagger.frame.dagger.module.DaggerMvpDemoModule;
import com.freak.httpmanage.mvpdagger.frame.mvp.contract.DaggerMvpDemoContract;
import com.freak.httpmanage.mvpdagger.frame.mvp.view.DaggerMvpDemoActivity;

import dagger.BindsInstance;
import dagger.Component;
@ActivityScope
@Component(modules = DaggerMvpDemoModule.class, dependencies = AppComponent.class)
public interface DaggerMvpDemoComponent {

    void inject(DaggerMvpDemoActivity daggerMvpDemoActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DaggerMvpDemoComponent.Builder view(DaggerMvpDemoContract.View view);

        DaggerMvpDemoComponent.Builder appComponent(AppComponent appComponent);

        DaggerMvpDemoComponent build();
    }
}
