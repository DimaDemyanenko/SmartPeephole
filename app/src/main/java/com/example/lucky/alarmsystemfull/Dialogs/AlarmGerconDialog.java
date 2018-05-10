package com.example.lucky.alarmsystemfull.Dialogs;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.R;

/**
 * Created by Lucky on 02.04.2018.
 */

public class AlarmGerconDialog extends DialogFragment {

    private EditText editText;
    private Button btnAlarmOff;

    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_alarm_gercon, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().setTitle(AppHelper.getStringForResources(R.string.door_was_opened));
        initViews(view);
        startAlarmSignal();
        showSoftKeyboard();
    }

    private void initViews(View v) {
        editText = v.findViewById(R.id.alarm_edit_text);
        btnAlarmOff = v.findViewById(R.id.alarm_off_signal);
        setListeners();
    }

    private void setListeners() {
        btnAlarmOff.setOnClickListener(new View.OnClickListener() {
            String savedPin = SharedPreferenceHelper.getPin(App.getContext());

            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals(savedPin)) {
                    stopAlarmSignal();
                    hideSoftKeyboard();
//                    startCameraActivity();
                    closeDialog();
//                    setResult(RESULT_OK);
                } else {
                    AppHelper.showMessage(AppHelper.getStringForResources(R.string.pin_not_correct));
                }
            }
        });
    }

    private void showSoftKeyboard() {
        editText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void hideSoftKeyboard() {
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void startCameraActivity() {
//        startActivity(new Intent(context, CameraActivity.class));
    }

    private void closeDialog() {
        getDialog().dismiss();
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

    public static AlarmGerconDialog newInstance() {
        return new AlarmGerconDialog();
    }
}
