package com.example.lucky.alarmsystemfull.Models;

import android.util.Log;

import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.NetworkHelper;
import com.example.lucky.alarmsystemfull.R;

import java.io.File;

/**
 * Created by Lucky on 29.03.2018.
 */

public class CallSensors {

    public void alertCall() {
        if(Sensors.getInstance().getLastVideo() != null){
            Sensors.getInstance().setPausesAllSensors();
            Sensors.getInstance().setCall(true);
            Sensors.getInstance().setSendFile(Sensors.getInstance().getLastVideo().getAbsolutePath());
            NetworkHelper.sendEmail(Sensors.getInstance().getLastVideo(),
                    AppHelper.getStringForResources(R.string.call));
            Log.d(Constants.TAG_DEBUG, AppHelper.getStringForResources(R.string.start_download));
        }
    }

    public void sensorCall(File file) {
        if (Sensors.getInstance().isCall()) {
            if(!Sensors.getInstance().getSendFile().equals(file.getAbsolutePath())){
                Sensors.getInstance().setCall(false);
                NetworkHelper.sendEmail(file, AppHelper.getStringForResources(R.string.call_continue));
            }
        }
    }
}
