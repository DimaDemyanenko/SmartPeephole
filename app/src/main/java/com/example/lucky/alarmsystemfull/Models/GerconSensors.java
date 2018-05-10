package com.example.lucky.alarmsystemfull.Models;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.NetworkHelper;
import com.example.lucky.alarmsystemfull.Presenters.CameraPresenter;
import com.example.lucky.alarmsystemfull.R;

import java.io.File;

/**
 * Created by Lucky on 29.03.2018.
 */

public class GerconSensors {

    public void alertGercon() {
        if (Sensors.getInstance().getLastVideo() != null) {
            Sensors.getInstance().setPausesAllSensors();
            Sensors.getInstance().setGercon(true);
            Sensors.getInstance().setSendFile(Sensors.getInstance().getLastVideo().getAbsolutePath());
            NetworkHelper.sendEmail(Sensors.getInstance().getLastVideo(),
                    AppHelper.getStringForResources(R.string.door_opened));
            // SmsHelper.sendSms(App.getContext(), "Открыли дверь");
            Log.d(Constants.TAG_DEBUG, AppHelper.getStringForResources(R.string.start_download));
            Sensors.getInstance().setGercon(false);
        }
    }

    public void sensorGercon(File file){

    }
}
