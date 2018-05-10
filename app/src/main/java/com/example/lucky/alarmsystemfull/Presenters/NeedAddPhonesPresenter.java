package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionPhonesListActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Views.NeedAddPhonesView;

/**
 * Created by Lucky on 25.02.2018.
 */

@InjectViewState
public class NeedAddPhonesPresenter extends MvpPresenter<NeedAddPhonesView> {

    public void openAddPhoneActivity(){
        getViewState().startActivity(new Intent(App.getContext(),
                OptionPhonesListActivity.class).putExtra(Constants.FIRST_START, true));
    }

}
