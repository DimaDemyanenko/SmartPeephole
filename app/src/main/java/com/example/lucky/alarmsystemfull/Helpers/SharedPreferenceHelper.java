package com.example.lucky.alarmsystemfull.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lucky on 03.02.2018.
 */

public class SharedPreferenceHelper {

    private final static String PREF_FILE = "PREF";

    public final static String PREF_ARRAY_PATHS = "ARRAY_PATHS";
    public final static String PREF_ARRAY_FILES = "ARRAY_FILES";

    public final static String PREF_PHONES = "PHONES";
    public final static String PREF_EMAILS = "EMAILS";

    public final static String PREF_BLUETOOTH_MAC_ADDRESS = "BLUETOOTH_MAC_ADDRESS";
    public final static String PREF_BLUETOOTH_NAME = "BLUETOOTH_NAME";

    public final static String PREF_EMAIL = "EMAIL";
    public final static String PREF_EMAIL_PASSWORD = "EMAIL_PASSWORD";

    public final static String PREF_PIN = "PIN";

    public final static String PREF_VIDEO_DURATION = "VIDEO_DURATION";

    public final static String PREF_ALL_VIDEO_FILES = "ALL_VIDEO_FILES";

    public final static String PREF_PAUSE_DURATION_ON_FIRST_MOTION_DETECTED = "PAUSE_DURATION_ON_MOTION_DETECTED";

    public final static String PREF_PAUSE_DURATION_ON_MOTION_SENSOR = "PAUSE_DURATION_ON_MOTION_SENSOR";

    public final static String PREF_PAUSE_DURATION_ON_CALL_AND_KNOCK_SENSORS = "PAUSE_DURATION_ON_CALL_AND_KNOCK_SENSORS";

    private static SharedPreferences.Editor getEditor(Context context){
        return context.getSharedPreferences(PREF_FILE, 0).edit();
    }

    public static void setStackVideos(Context context, StringBuilder value){
        getEditor(context).putString(PREF_ARRAY_PATHS, value.toString()).apply();
    }

    public static String getStackVideos(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_ARRAY_PATHS, null);
    }


    public static void setStackFiles(Context context, StringBuilder value){
        getEditor(context).putString(PREF_ARRAY_FILES, value.toString()).apply();
    }

    public static String getStackFiles(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_ARRAY_FILES, null);
    }


    public static void setPhones(Context context, StringBuilder value){
        getEditor(context).putString(PREF_PHONES, value.toString()).apply();
    }

    public static String getPhones(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_PHONES, "");
    }

    public static void setEMails(Context context, StringBuilder value){
        getEditor(context).putString(PREF_EMAILS, value.toString()).apply();
    }

    public static String getEmails(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_EMAILS, null);
    }

    public static void setBluetoothMacAddress(Context context, String value){
        getEditor(context).putString(PREF_BLUETOOTH_MAC_ADDRESS, value).apply();
    }

    public static String getBluetoothMacAddress(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_BLUETOOTH_MAC_ADDRESS, "");
    }

    public static void setBluetoothName(Context context, String value){
        getEditor(context).putString(PREF_BLUETOOTH_NAME, value).apply();
    }

    public static String getBluetoothName(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_BLUETOOTH_NAME, null);
    }

    public static void setEMail(Context context, String value){
        getEditor(context).putString(PREF_EMAIL, value).apply();
    }

    public static String getEmail(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_EMAIL, "");
    }

    public static void setEmailPassword(Context context, String value){
        getEditor(context).putString(PREF_EMAIL_PASSWORD, value).apply();
    }

    public static String getEmailPassword(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_EMAIL_PASSWORD, "");
    }

    public static void setPin(Context context, String value){
        getEditor(context).putString(PREF_PIN, value).apply();
    }

    public static String getPin(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_PIN, "");
    }

    public static void setVideoDuration(Context context, long value){
        getEditor(context).putLong(PREF_VIDEO_DURATION, value).apply();
    }

    public static long getVideoDuration(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getLong(PREF_VIDEO_DURATION, 7000);
    }

    public static void setAllVideoFiles(Context context, StringBuilder value){
        getEditor(context).putString(PREF_ALL_VIDEO_FILES, value.toString()).apply();
    }

    public static String getAllVideoFiles(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getString(PREF_ALL_VIDEO_FILES, null);
    }

    public static void setPauseDurationOnFirstMotionDetected(Context context, long value){
        getEditor(context).putLong(PREF_PAUSE_DURATION_ON_FIRST_MOTION_DETECTED, value).apply();
    }

    public static long getPauseDurationOnFirstMotionDetected(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getLong(PREF_PAUSE_DURATION_ON_FIRST_MOTION_DETECTED, 10000);
    }

    public static void setPauseDurationOnMotionSensor(Context context, long value){
        getEditor(context).putLong(PREF_PAUSE_DURATION_ON_MOTION_SENSOR, value).apply();
    }

    public static long getPauseDurationOnMotionSensor(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getLong(PREF_PAUSE_DURATION_ON_MOTION_SENSOR, 90000);
    }

    public static void setPauseDurationOnCallAndKnockSensors(Context context, long value){
        getEditor(context).putLong(PREF_PAUSE_DURATION_ON_CALL_AND_KNOCK_SENSORS, value).apply();
    }

    public static long getPauseDurationOnCallAndKnockSensors(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE, 0);
        return pref.getLong(PREF_PAUSE_DURATION_ON_CALL_AND_KNOCK_SENSORS, 90000);
    }

}