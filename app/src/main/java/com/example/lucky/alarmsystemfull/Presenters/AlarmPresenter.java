package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.CameraActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.AlarmView;

/**
 * Created by Lucky on 01.04.2018.
 */

@InjectViewState
public class AlarmPresenter extends MvpPresenter<AlarmView> {

    private MediaPlayer mediaPlayer;

    public AlarmPresenter() {

    }

    public void startAlarmSignal() {
        mediaPlayer = MediaPlayer.create(App.getContext(), R.raw.signal);
        mediaPlayer.start();
    }

    public void stopAlarmSignal() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public void showSoftKeyboard() {
        getViewState().showSoftKeyboard();
    }

    public void hideSoftKeyboard() {
        getViewState().hideSoftKeyboard();
    }

    public void startCameraActivity(Context context) {
        context.startActivity(new Intent(context, CameraActivity.class));
    }


}
