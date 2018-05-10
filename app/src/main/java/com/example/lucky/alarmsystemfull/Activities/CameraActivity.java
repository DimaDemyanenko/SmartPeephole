package com.example.lucky.alarmsystemfull.Activities;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.CameraVIewLibrary.Control;
import com.example.lucky.alarmsystemfull.CameraVIewLibrary.ControlView;
import com.example.lucky.alarmsystemfull.Dialogs.AlarmGerconDialog;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper;
import com.example.lucky.alarmsystemfull.Helpers.ImageHelper;
import com.example.lucky.alarmsystemfull.Presenters.BluetoothPresenter;
import com.example.lucky.alarmsystemfull.Presenters.CameraPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.myCameraView;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.SessionType;
import com.otaliastudios.cameraview.VideoQuality;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class CameraActivity extends MvpAppCompatActivity implements myCameraView, ControlView.Callback {

    private static final int START_ALARM_ACTIVITY = 555;

    @InjectPresenter
    CameraPresenter cameraPresenter;

    @InjectPresenter
    BluetoothPresenter bluetoothPresenter;

    private CameraView camera;
    private ViewGroup controlPanel;
    private TextView txtStatus;
    private View btnCaptureCamera;
    private  View btnPauseCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_camera);
        CameraLogger.setLogLevel(CameraLogger.LEVEL_VERBOSE);
        initViews();
//        cameraPresenter.bluetoothOff();
        cameraPresenter.initCamera();
        camera.start();
        cameraPresenter.bluetoothInit();
    }

    private void initViews(){
        txtStatus = findViewById(R.id.stoped);
        camera = findViewById(R.id.camera);
        btnCaptureCamera = findViewById(R.id.capturePhoto);
        btnPauseCamera = findViewById(R.id.pauseCamera);
        controlPanel = findViewById(R.id.controls);

        setListeners();
        setCameraListener();
        initControlPanel();

        cameraPresenter.setStatusViewVisibility();
        cameraPresenter.setStatusViewText(AppHelper.getStringForResources(R.string.capture_video_stopped));
    }

    private void setListeners(){
        btnCaptureCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraPresenter.startWork();
            }
        });

        btnPauseCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraPresenter.pauseWork();
            }
        });
    }

    private void setCameraListener(){
        camera.addCameraListener(new CameraListener() {
            public void onCameraOpened(CameraOptions options) {
                onOpened();
            }

            public void onPictureTaken(byte[] jpeg) {
                jpeg = ImageHelper.compressPhoto(jpeg);
//                onPicture(jpeg);
            }

            @Override
            public void onVideoTaken(File video) {
                super.onVideoTaken(video);
                AppHelper.delay(100);
                cameraPresenter.checkInternetConnection();
                cameraPresenter.setStatusViewText(AppHelper
                        .getStringForResources(R.string.capture_video_stopped));
                cameraPresenter.checkFreeSizeStorage();
                cameraPresenter.checkSensors(video);

                if (cameraPresenter.getStatus()){
                    cameraPresenter.newCycle();
                }
            }
        });
    }

    private void initControlPanel(){
        controlPanel = findViewById(R.id.controls);
        ViewGroup group = (ViewGroup) controlPanel.getChildAt(0);
        Control[] controls = Control.values();
        for (Control control : controls) {
            ControlView view = new ControlView(App.getContext(), control, this);
            group.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        controlPanel.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                BottomSheetBehavior b = BottomSheetBehavior.from(controlPanel);
                b.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    private void onOpened() {
        ViewGroup group = (ViewGroup) controlPanel.getChildAt(0);
        for (int i = 0; i < group.getChildCount(); i++) {
            ControlView view = (ControlView) group.getChildAt(i);
//            view.onCameraOpened(camera);
        }
    }

    @Override
    public void setStatusViewVisibility(boolean state) {
        txtStatus.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setStatusViewText(String text) {
        txtStatus.setText(text);
    }

    @Override
    public void initCamera(SessionType sessionType, VideoQuality videoQuality) {
        camera.setSessionType(sessionType);
        camera.setVideoQuality(videoQuality);
    }

    @Override
    public void setButtonsVisibility(boolean state) {
        btnCaptureCamera.setVisibility(state ? View.VISIBLE : View.GONE);
        btnPauseCamera.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void startCapturingVideo(File file, long duration) {
        camera.startCapturingVideo(file, duration);
    }

    @Override
    public void restartCamera() {
        camera.stop();
        camera.start();
    }

    @Override
    public void stopCamera() {
        camera.stop();
//        cameraPresenter.pauseWork();
    }

    @Override
    public void startAlarmActivity() {
//        startActivityForResult(new Intent(this, AlarmActivity.class), START_ALARM_ACTIVITY);
//        cameraPresenter.bluetoothOff();
//        cameraPresenter.disconnectBluetooth();
//        cameraPresenter.setBluetooth(false);
//        startActivity(new Intent(this, AlarmActivity.class));
//        finish();
        AlarmGerconDialog.newInstance()
                .show(getSupportFragmentManager(), "alarm_gercon_dialog");
    }

    @Override
    public void showStartWorkToast() {
        AppHelper.showMessage(AppHelper.getStringForResources(R.string.start_video_capture));
    }

    @Override
    public boolean onValueChanged(Control control, Object value, String name) {
        if (!camera.isHardwareAccelerated()) {
            if ((Integer) value > 0) {
                AppHelper.showMessage("This device does not support hardware acceleration. " +
                        "In this case you can not change width or height. " +
                        "The view will act as WRAP_CONTENT by default.");
                return false;
            }
        }
        control.applyValue(camera, value);
        BottomSheetBehavior b = BottomSheetBehavior.from(controlPanel);
        b.setState(BottomSheetBehavior.STATE_HIDDEN);
        AppHelper.showMessage("Changed " + control.getName() + " to " + name);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {

            switch (requestCode){
//                case START_ALARM_ACTIVITY:
//                    if( resultCode == RESULT_OK){
//                        camera.start();
//                        cameraPresenter.startWork();
//                    }
//                    break;
                case BluetoothHelper.REQUEST_ENABLE_BT:
                    if (resultCode == RESULT_OK) {
                        cameraPresenter.bluetoothConnection();
                    } else {
                        AppHelper.showMessage(AppHelper.getStringForResources(R.string.need_bluetooth));
                    }
                    break;
            }
    }

    @Override
    public void onBackPressed() {
        BottomSheetBehavior b = BottomSheetBehavior.from(controlPanel);
        if (b.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            b.setState(BottomSheetBehavior.STATE_HIDDEN);
            return;
        }
        super.onBackPressed();
        camera.destroy();
//        cameraPresenter.bluetoothOff();
        cameraPresenter.setBluetooth(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        camera.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        camera.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        camera.destroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean valid = true;
        for (int grantResult : grantResults) {
            valid = valid && grantResult == PackageManager.PERMISSION_GRANTED;
        }
        if (valid && !camera.isStarted()) {
//            camera.start();
        }
    }
}
