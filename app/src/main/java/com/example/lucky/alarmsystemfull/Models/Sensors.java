package com.example.lucky.alarmsystemfull.Models;

import com.example.lucky.alarmsystemfull.Threads.PauseCallAndKnockSensorsThread;
import com.example.lucky.alarmsystemfull.Threads.PauseMotionSensorThread;

import java.io.File;

/**
 * Created by Lucky on 29.03.2018.
 */

public class Sensors {

    private boolean isMotion;
    private boolean isCall;
    private boolean isKnock;
    private boolean isGercon;

    private File lastVideo;
    private String sendFile;

    private boolean isPauseMotion;            // надо ли выставлять паузу для работы датчика движения
    private boolean isPauseCallAndKnock;      // и для датчиков звонка/стука

    PauseMotionSensorThread pauseMotionSensorThread = new PauseMotionSensorThread();
    PauseCallAndKnockSensorsThread pauseCallAndKnockSensorsThread = new PauseCallAndKnockSensorsThread();

    private static Sensors instance;

    public static Sensors getInstance() {
        if (instance == null) {
            instance = new Sensors();
        }
        return instance;
    }

    public boolean isMotion() {
        return isMotion;
    }

    public void setMotion(boolean motion) {
        isMotion = motion;
    }

    public boolean isCall() {
        return isCall;
    }

    public void setCall(boolean call) {
        isCall = call;
    }

    public boolean isKnock() {
        return isKnock;
    }

    public void setKnock(boolean knock) {
        isKnock = knock;
    }

    public boolean isGercon() {
        return isGercon;
    }

    public void setGercon(boolean gercon) {
        isGercon = gercon;
    }

    public File getLastVideo() {
        return lastVideo;
    }

    public void setLastVideo(File lastVideo) {
        this.lastVideo = lastVideo;
    }

    public String getSendFile() {
        return sendFile;
    }

    public void setSendFile(String sendFile) {
        this.sendFile = sendFile;
    }

    public boolean isPauseMotion() {
        return isPauseMotion;
    }

    public boolean isPauseCallAndKnock() {
        return isPauseCallAndKnock;
    }

    public void setPauseMotion(boolean pauseMotion) {
        isPauseMotion = pauseMotion;
    }

    public void setPauseCallAndKnock(boolean pauseCallAndKnock) {
        isPauseCallAndKnock = pauseCallAndKnock;
    }

    public void setPausesAllSensors() {
        pauseMotionOn();
        pauseCallAndKnockOn();
    }

    /*
    Выставляю паузу для датчика движения
     */
    public void pauseMotionOn() {
        if (!isPauseMotion) {
            pauseMotionSensorThread.startThread();
        } else {
            pauseMotionSensorThread.stopThread();
            pauseMotionSensorThread.startThread();
        }
    }

    /*
    Выставляю паузу для звонка и стука
    Если переменная еще не выставлена,
    Значит запускается поток после срабатывания датчика движения
    Если уже выставлена, значит, датчик движения ее выставил
    Но теперь позвонили/постучали
    И надо запустить отсчет заново, поэтому
    останавливаю поток и запускаю заново
     */
    private void pauseCallAndKnockOn() {
        if (!isPauseCallAndKnock) {
            pauseCallAndKnockSensorsThread.startThread();
        } else {
            pauseCallAndKnockSensorsThread.stopThread();
            pauseCallAndKnockSensorsThread.startThread();
        }
    }
}
