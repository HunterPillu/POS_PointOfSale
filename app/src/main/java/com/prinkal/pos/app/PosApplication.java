package com.prinkal.pos.app;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class PosApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
