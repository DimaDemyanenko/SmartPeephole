package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.CameraActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.Views.OptionPinView;

/**
 * Created by Lucky on 27.02.2018.
 */

@InjectViewState
public class OptionPinPresenter extends MvpPresenter<OptionPinView> {

    public void savePin(String pin){
        SharedPreferenceHelper.setPin(App.getContext(),pin);
        getViewState().setBtnNextEnable(true);
    }

    public void openActivity(){
        getViewState().startActivity(new Intent(App.getContext(), CameraActivity.class));
    }

    public void checkFirstStart(boolean isFirstStart){
        getViewState().setBtnNextEnable(isFirstStart);
        if (!isFirstStart) {
            // TODO реализовать изменение пин-кода
        }
    }

}
