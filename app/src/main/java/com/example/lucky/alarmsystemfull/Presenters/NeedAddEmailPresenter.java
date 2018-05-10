package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionEmailActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Views.NeedAddEmailView;

/**
 * Created by Lucky on 25.02.2018.
 */

@InjectViewState
public class NeedAddEmailPresenter extends MvpPresenter<NeedAddEmailView> {

    public void openAddEmailActivity() {
        getViewState().startActivity(new Intent(App.getContext(), OptionEmailActivity.class)
                .putExtra(Constants.FIRST_START, true));
    }
}
