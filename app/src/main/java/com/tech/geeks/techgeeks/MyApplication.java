package com.tech.geeks.techgeeks;

import android.app.Application;
import android.content.Context;

/**
 * This class inherits Application. It will be used to get the Application's context.
 * For example we need Application context when creating a RequestQueue using Volley
 */

public class MyApplication extends Application {

    // Instance of this class
    private static MyApplication mInstance;
    // Application Context
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        // Initializing mAppContext with this Application's context
        this.mAppContext = getApplicationContext();
    }

    // getter for Instance of this class
    public static MyApplication getInstance() {
        return mInstance;
    }

    // getter for Application context
    public static Context getAppContext() {
        return mAppContext;
    }
}
