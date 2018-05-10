package com.example.lucky.alarmsystemfull.Helpers;

import android.content.Context;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by Lucky on 28.02.2018.
 */

public class SmsHelper {

    public static void sendSms(Context context, String text){
        ArrayList<String> phonesList = new ArrayList<>();

        String s = "";

        String phone = SharedPreferenceHelper.getPhones(context);
        if (phone != null) {
            String[] videoArray = phone.split(",");
            for (int i = 0; i < videoArray.length; i++) {
                s = videoArray[i].replace("(", "")
                        .replace(")", "").replace("-", "");
//                phonesList.add(s.substring(2));
                phonesList.add(s);
            }
        }

        for (int i = 0; i < phonesList.size(); i++){
            SmsManager.getDefault().sendTextMessage(phonesList.get(i),
                    null, text, null,null);
        }
    }
}
