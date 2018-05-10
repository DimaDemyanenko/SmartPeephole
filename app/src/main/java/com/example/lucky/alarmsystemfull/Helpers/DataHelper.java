package com.example.lucky.alarmsystemfull.Helpers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucky on 10.02.2018.
 */

public class DataHelper {

    public static List<String> loadPhonesFromSharedPreference(Context context) {
        List<String> phonesList = new ArrayList<>();
        String phones = SharedPreferenceHelper.getPhones(context);
        if (!phones.isEmpty()) {
            String[] phonesArray = phones.split(",");
            for (int i = 0; i < phonesArray.length; i++) {
                if (!phonesArray[i].isEmpty()){
                    phonesList.add(phonesArray[i]);
                }
            }
        }
        return phonesList;
    }

    public static void savePhonesToSharedPreference(Context context, List<String> phonesList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < phonesList.size(); i++) {
            sb.append(phonesList.get(i)).append(",");
        }
        SharedPreferenceHelper.setPhones(context, sb);
    }

}
