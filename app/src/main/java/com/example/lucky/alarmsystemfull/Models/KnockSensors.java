package com.example.lucky.alarmsystemfull.Models;

import android.util.Log;

import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.NetworkHelper;
import com.example.lucky.alarmsystemfull.R;

import java.io.File;

/**
 * Created by Lucky on 26.03.2018.
 */

//public class KnockSensors extends Sensors  {
public class KnockSensors {

    public void alertKnock() {
        if (Sensors.getInstance().getLastVideo() != null) {
            Sensors.getInstance().setPausesAllSensors();
            Sensors.getInstance().setKnock(true);
            Sensors.getInstance().setSendFile(Sensors.getInstance().getLastVideo().getAbsolutePath());
            NetworkHelper.sendEmail(Sensors.getInstance().getLastVideo(),
                    AppHelper.getStringForResources(R.string.knock));
            Log.d(Constants.TAG_DEBUG, AppHelper.getStringForResources(R.string.start_download));
        }
    }

    public void sensorKnock(File file) {
        if (Sensors.getInstance().isKnock()) {
            if (!Sensors.getInstance().getSendFile().equals(file.getAbsolutePath())) {
                Sensors.getInstance().setKnock(false);
                NetworkHelper.sendEmail(file,
                        AppHelper.getStringForResources(R.string.knock_continue));
            }
        }
    }
}
