package com.example.lucky.alarmsystemfull.Helpers;

import android.content.Context;
import android.widget.Toast;

import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.R;

/**
 * Created by Lucky on 25.02.2018.
 */

public class AppHelper {

    public static void showMessage(String message){
        Toast.makeText(App.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getStringForResources(int resourceId){
        return App.getContext().getResources().getString(resourceId);
    }

}
