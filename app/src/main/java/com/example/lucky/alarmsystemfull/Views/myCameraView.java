package com.example.lucky.alarmsystemfull.Views;

import com.arellomobile.mvp.MvpView;
import com.otaliastudios.cameraview.SessionType;
import com.otaliastudios.cameraview.VideoQuality;

import java.io.File;

/**
 * Created by Lucky on 27.02.2018.
 */

public interface myCameraView extends StartActivityInterface {

    void setStatusViewVisibility(boolean state);
    void setStatusViewText(String text);
    void initCamera(SessionType sessionType, VideoQuality videoQuality);
    void setButtonsVisibility(boolean state);
    void startCapturingVideo(File file, long duration);
    void restartCamera();
    void stopCamera();
    void startAlarmActivity();
    void showStartWorkToast();

}
