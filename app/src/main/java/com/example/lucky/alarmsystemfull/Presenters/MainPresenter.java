package com.example.lucky.alarmsystemfull.Presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.CameraActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedAddEmailActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedAddPhonesActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedChooseBluetoothActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedPinActivityActivity;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionsActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.DataHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.Views.MainView;

import java.util.List;

/**
 * Created by Lucky on 25.02.2018.
 */

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private List<String> phones;
    private String email;
    private String bluetoothMacAddress;
    private String pin;

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.SEND_SMS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void loadDate() {
        phones = DataHelper.loadPhonesFromSharedPreference(App.getContext());
        email = SharedPreferenceHelper.getEmail(App.getContext());
        bluetoothMacAddress = SharedPreferenceHelper.getBluetoothMacAddress(App.getContext());
        pin = SharedPreferenceHelper.getPin(App.getContext());
    }

    public void openActivity() {
        loadDate();
        if (phones.isEmpty()) {
            getViewState().startActivity(new Intent(App.getContext(), NeedAddPhonesActivity.class));
        } else if (email.isEmpty()) {
            getViewState().startActivity(new Intent(App.getContext(), NeedAddEmailActivity.class));
        } else if (bluetoothMacAddress.isEmpty()) {
            getViewState().startActivity(new Intent(App.getContext(), NeedChooseBluetoothActivity.class));
        } else if (pin.isEmpty()) {
            getViewState().startActivity(new Intent(App.getContext(), NeedPinActivityActivity.class));
        } else {
            getViewState().startActivity(new Intent(App.getContext(), CameraActivity.class));
        }
    }

    public void openOptions() {
        getViewState().startActivity(new Intent(App.getContext(), OptionsActivity.class));
    }

    public void checkPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                permissions,
                1);
    }


}

