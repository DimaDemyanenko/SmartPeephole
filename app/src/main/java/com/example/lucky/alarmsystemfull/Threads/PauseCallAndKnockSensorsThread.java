package com.example.lucky.alarmsystemfull.Threads;

import android.util.Log;

import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.Models.Sensors;

/**
 * Created by Lucky on 31.03.2018.
 */

public class PauseCallAndKnockSensorsThread {

    private Thread pauseCalAndKnockThread;

    private void initThread(){
        pauseCalAndKnockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(Constants.TAG_DEBUG, "Запустился поток ЗВОНКА И СТУКА");
                Sensors.getInstance().setPauseCallAndKnock(true);

                sleepThread();

                Sensors.getInstance().setPauseCallAndKnock(false);
                Thread.currentThread().interrupt();

                Log.d(Constants.TAG_DEBUG, "пауза закончена ЗВОНОКА И СТУКА");
            }
        });
    }

    public void startThread(){
        initThread();
        pauseCalAndKnockThread.start();
    }

    public void stopThread(){
        if(pauseCalAndKnockThread != null){
            pauseCalAndKnockThread.interrupt();
            pauseCalAndKnockThread = null;
        }
    }

    private void sleepThread(){
        try {
            Thread.currentThread().sleep(
                    SharedPreferenceHelper.getPauseDurationOnCallAndKnockSensors(App.getContext()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
