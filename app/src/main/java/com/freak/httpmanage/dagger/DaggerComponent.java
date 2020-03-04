package com.freak.httpmanage.dagger;


import com.freak.httpmanage.MainActivity;
import com.freak.httpmanage.rxbus.RxBusContract;

import dagger.BindsInstance;
import dagger.Component;

@Component(modules = {DaggerModule.class})
public interface DaggerComponent {
    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DaggerComponent.Builder view(RxBusContract.View view);
        DaggerComponent build();
    }

}
