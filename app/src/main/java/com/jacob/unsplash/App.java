package com.jacob.unsplash;

import android.app.Application;
import android.content.Context;

/**
 * Created by vynnykiakiv on 3/24/18.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }


}
