package com.example.lucky.alarmsystemfull.Models;

import com.example.lucky.alarmsystemfull.Presenters.CameraPresenter;

import java.io.File;

/**
 * Created by Lucky on 29.03.2018.
 */

public class SensorsLogic {

    private KnockSensors knockSensor;
    private CallSensors callSensor;
    private GerconSensors gerconSensor;
    private MotionSensors motionSensor;

    private CameraPresenter listener;

    public SensorsLogic(CameraPresenter context){
        listener = context;
        motionSensor = new MotionSensors();
        callSensor = new CallSensors();
        knockSensor = new KnockSensors();
        gerconSensor = new GerconSensors();
    }

    public void checkSensors(File file) {
        Sensors.getInstance().setLastVideo(file);
        motionSensor.sensorMotion(file);
        callSensor.sensorCall(file);
        knockSensor.sensorKnock(file);
        gerconSensor.sensorGercon(file);
    }

    /*
    Проверяю, если ранее никакой датчик
    не сработал (а это значит, что второе видео
    отправлять не должно),
    то вызываю соответствующий сработавшему
    датчику метод
     */
    public void sensorDetect(String code) {
        if(code.equals("B")){
            listener.stopCamera();
            gerconSensor.alertGercon();
        }

        if (!Sensors.getInstance().isPauseCallAndKnock()
                && !Sensors.getInstance().isCall()
                && !Sensors.getInstance().isKnock()
                && !Sensors.getInstance().isGercon()) {
            switch (code) {
                case "H":
                    if(!Sensors.getInstance().isPauseMotion()){
                        motionSensor.alertMotion();
                    }
                    break;
                case "Y":
                    knockSensor.alertKnock();
                    break;
                case "N":
                    callSensor.alertCall();
                    break;
                case "B":

                    break;
            }
        }
    }

    public interface onClick{
        void stopCamera();
    }
}
