package com.example.lucky.alarmsystemfull.App;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Lucky on 25.02.2018.
 */

public class App extends Application {

    private static App instance;

    public static Context getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
