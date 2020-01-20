package com.freak.httpmanage;

import com.freak.httpmanage.rxbus.RxBusActivity;
import com.freak.httpmanage.rxbus.RxBusPresenter;

import dagger.Component;

@Component(modules = {RxBusPresenter.class,MainPresenter.class})
public interface MainComponent {
     void inject(RxBusActivity rxBusActivity);
     void inject(MainActivity mainActivity);
}
