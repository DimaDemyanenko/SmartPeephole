package com.example.lucky.alarmsystemfull.Models;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.NetworkHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.Presenters.CameraPresenter;
import com.example.lucky.alarmsystemfull.R;

import java.io.File;

/**
 * Created by Lucky on 26.03.2018.
 */

//public class MotionSensors extends Sensors {
public class MotionSensors  {

    private boolean isFirstMotion;
    private boolean isMotionDetected;
    private boolean isTimer;

    public void alertMotion() {
        setMotionFlag();
        if(!isFirstMotion) {
            // Переход во второй режим работы - Ожидания
            isFirstMotion = true;
            startTimerWhenMotionDetected();
        }
    }

    private void setMotionFlag(){
        isMotionDetected = true;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                isMotionDetected = false;
            }
        };
        handler.postDelayed(r, 1000);
    }

    private void startTimerWhenMotionDetected(){
        Handler handler = new Handler();
        // Переход в Третий режим работы - Повторная фиксация движения
        Runnable r = new Runnable() {
            public void run() {
                startTimer();
            }
        };
        handler.postDelayed(r,
                SharedPreferenceHelper.getPauseDurationOnFirstMotionDetected(App.getContext()));
    }

    public void startTimer(){
        isTimer = true;
        new CountDownTimer(5000, 100) {
            public void onTick(long millisUntilFinished) {
                if(isMotionDetected && isTimer){
                    sendVideo();
                    isTimer = false;
                    setNewCycleFlag();
                }
            }
            public void onFinish() {
                setNewCycleFlag();
            }
        }.start();
    }


    public void sendVideo(){
        if(Sensors.getInstance().getLastVideo() != null ){
            // Переход в Четвертый режим работы - подготовка к новому циклу
            Sensors.getInstance().pauseMotionOn();
            Sensors.getInstance().setMotion(true);
            Sensors.getInstance().setSendFile(Sensors.getInstance().getLastVideo().getAbsolutePath());
            NetworkHelper.sendEmail(Sensors.getInstance().getLastVideo(),
                    AppHelper.getStringForResources(R.string.motion));
        }
    }

    private void setNewCycleFlag(){
        isFirstMotion = false;
    }

    public void sensorMotion(File file){
        if (Sensors.getInstance().isMotion()) {
            if(!Sensors.getInstance().getSendFile().equals(file.getAbsolutePath())){
                Sensors.getInstance().setMotion(false);
                NetworkHelper.sendEmail(file,
                        AppHelper.getStringForResources(R.string.motion_continue));
            }
        }
    }









}
