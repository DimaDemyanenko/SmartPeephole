package com.example.lucky.alarmsystemfull.Threads;

import android.util.Log;

import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.Models.Sensors;

/**
 * Created by Lucky on 31.03.2018.
 */

public class PauseMotionSensorThread {

    private Thread pauseMotionThread;

    private void initThread() {

        pauseMotionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(Constants.TAG_DEBUG, "Запустился поток ДВИЖЕНИЯ");
                Sensors.getInstance().setPauseMotion(true);

                sleepThread();

                Sensors.getInstance().setPauseMotion(false);
                Thread.currentThread().interrupt();

                Log.d(Constants.TAG_DEBUG, "закончилась пауза ДВИЖЕНИЯ");
            }
        });

    }

    public void startThread() {
        initThread();
        pauseMotionThread.start();
    }

    public void stopThread() {
        if (pauseMotionThread != null) {
            pauseMotionThread.interrupt();
            pauseMotionThread = null;
        }
    }

    private void sleepThread() {
        try {
            Thread.currentThread().sleep(SharedPreferenceHelper
                    .getPauseDurationOnMotionSensor(App.getContext()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
