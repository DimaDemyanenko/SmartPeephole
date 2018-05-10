package com.example.lucky.alarmsystemfull.Views;

import android.bluetooth.BluetoothDevice;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Lucky on 26.02.2018.
 */

public interface OptionBluetoothView extends StartActivityInterface {

    void setBluetoothStatus(String text);
//    boolean isFirstStart();
    void closeActivity();
    void addNewBluetoothDevice(BluetoothDevice device);
    void btArrayAdapterClear();
}
