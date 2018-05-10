package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionPinActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Views.NeedPinView;

/**
 * Created by Lucky on 27.02.2018.
 */

@InjectViewState
public class NeedPinPresenter extends MvpPresenter<NeedPinView> {

    public void openActivity(){
        getViewState().startActivity(new Intent(App.getContext(), OptionPinActivity.class)
                .putExtra(Constants.FIRST_START, true));
    }
}
