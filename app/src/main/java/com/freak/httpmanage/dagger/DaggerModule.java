package com.freak.httpmanage.dagger;

import com.freak.httpmanage.main.MainActivity;

import dagger.Module;

@Module
public class DaggerModule {
    private MainActivity mainActivity;


    public DaggerModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
