package com.intkhabahmed.bakenshake.utils;

import android.app.Application;

import com.intkhabahmed.bakenshake.locals.AppDatabase;

public class Global extends Application {

    private static Global sGlobalInstance;

    public static Global getInstance() {
        return sGlobalInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sGlobalInstance = (Global) getApplicationContext();
    }

    public static AppDatabase getDbInstance() {
        return AppDatabase.getInstance(sGlobalInstance);
    }
}
