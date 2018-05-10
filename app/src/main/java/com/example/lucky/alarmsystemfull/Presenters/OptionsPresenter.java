package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionBluetoothActivity;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionEmailActivity;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionPhonesListActivity;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionPinActivity;
import com.example.lucky.alarmsystemfull.Activities.Options.VideoDurationActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Views.OptionsView;

/**
 * Created by Lucky on 26.02.2018.
 */

@InjectViewState
public class OptionsPresenter extends MvpPresenter<OptionsView> {

    public void openAddPhonesActivity(){
        getViewState().startActivity(new Intent(App.getContext(), OptionPhonesListActivity.class));
    }

    public void openAddEmailActivity(){
        getViewState().startActivity(new Intent(App.getContext(), OptionEmailActivity.class));
    }

    public void openBluetoothOptionsActivity(){
        getViewState().startActivity(new Intent(App.getContext(), OptionBluetoothActivity.class));
    }

    public void openPinActivity(){
        getViewState().startActivity(new Intent(App.getContext(), OptionPinActivity.class));
    }

    public void openVideoDurationActivity(){
        getViewState().startActivity(new Intent(App.getContext(), VideoDurationActivity.class));
    }
}
