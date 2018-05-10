package com.example.lucky.alarmsystemfull.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.Presenters.AlarmPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.AlarmView;

public class AlarmActivity extends MvpAppCompatActivity implements AlarmView {

    @InjectPresenter
    AlarmPresenter alarmPresenter;

    private EditText editText;
    private Button btnAlarmOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        initViews();

        alarmPresenter.showSoftKeyboard();
        alarmPresenter.startAlarmSignal();
    }

    private void initViews(){
        editText = findViewById(R.id.alarm_edit_text);
        btnAlarmOff = findViewById(R.id.alarm_off_signal);
        setListeners();
    }

    private void setListeners(){
        btnAlarmOff.setOnClickListener(new View.OnClickListener() {
            String savedPin = SharedPreferenceHelper.getPin(App.getContext());
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().equals(savedPin)){
                    alarmPresenter.stopAlarmSignal();
                    alarmPresenter.hideSoftKeyboard();
                    alarmPresenter.startCameraActivity(AlarmActivity.this);
//                    setResult(RESULT_OK);
                    finish();
                } else {
                    AppHelper.showMessage(AppHelper.getStringForResources(R.string.pin_not_correct));
                }
            }
        });
    }

    @Override
    public void showSoftKeyboard() {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
