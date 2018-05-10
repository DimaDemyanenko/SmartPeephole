package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.CameraActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedChooseBluetoothActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedPinActivityActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.OptionEmailView;

/**
 * Created by Lucky on 26.02.2018.
 */

@InjectViewState
public class OptionEmailPresenter extends MvpPresenter<OptionEmailView> {

    private String macAddress;
    private String pin;

    private void loadDate() {
        macAddress = SharedPreferenceHelper.getBluetoothMacAddress(App.getContext());
        pin = SharedPreferenceHelper.getPin(App.getContext());
    }

    public void checkFirstStart(boolean isFirstStart) {
        getViewState().setBtnNextVisibility(isFirstStart);
        if (!isFirstStart) {
            getViewState().firstStart(SharedPreferenceHelper.getEmail(App.getContext()),
                    SharedPreferenceHelper.getEmailPassword(App.getContext()));
        }
    }

    public void checkValidEmail(String email, String password) {
        if (!isValidEmail(email)) {
            AppHelper.showMessage(AppHelper.getStringForResources(R.string.check_email));
        } else if (password.isEmpty()) {
            AppHelper.showMessage(AppHelper.getStringForResources(R.string.check_password));
        } else {
            safeNewEmailToSharedPreference(email, password);
            AppHelper.showMessage(AppHelper.getStringForResources(R.string.email_saved));
            setBtnNextEnable(true);
        }
    }

    public void setBtnNextEnable(boolean state) {
        getViewState().btnNextSetEnable(state);
    }

    private void safeNewEmailToSharedPreference(String email, String password) {
        SharedPreferenceHelper.setEMail(App.getContext(), email);
        SharedPreferenceHelper.setEmailPassword(App.getContext(), password);
    }

    private boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void openActivity() {
        loadDate();
        if (macAddress.isEmpty()) {
            getViewState().startActivity(new Intent(App.getContext(), NeedChooseBluetoothActivity.class));
        } else if (pin.isEmpty()) {
            getViewState().startActivity(new Intent(App.getContext(), NeedPinActivityActivity.class));
        } else {
            getViewState().startActivity(new Intent(App.getContext(), CameraActivity.class));
        }
    }

}
