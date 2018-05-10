package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionBluetoothActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Views.NeedBluetoothView;

/**
 * Created by Lucky on 26.02.2018.
 */

@InjectViewState
public class NeedBluetoothPresenter extends MvpPresenter<NeedBluetoothView> {

    public void openActivity(){
        getViewState().startActivity(new Intent(App.getContext(), OptionBluetoothActivity.class)
                .putExtra(Constants.FIRST_START, true));
    }

}
