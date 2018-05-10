package com.example.lucky.alarmsystemfull.Presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.VideoDurationView;

/**
 * Created by Lucky on 28.02.2018.
 */

@InjectViewState
public class VideoDurationPresenter extends MvpPresenter<VideoDurationView> {

    public void loadVideoDuration() {
        getViewState().setVideoDuration(String.valueOf(SharedPreferenceHelper
                .getVideoDuration(App.getContext()) / 1000 - 1));
    }

    public void safeVideoDuration(String duration) {
        SharedPreferenceHelper.setVideoDuration(App.getContext(),
                (Long.valueOf(duration) +1 ) * 1000);
        AppHelper.showMessage(AppHelper.getStringForResources(R.string.saved));
    }
}
