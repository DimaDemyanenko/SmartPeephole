package com.example.lucky.alarmsystemfull.Activities.Options;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.Presenters.OptionsPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.OptionsView;

public class OptionsActivity extends MvpAppCompatActivity implements OptionsView {

    @InjectPresenter
    OptionsPresenter optionsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        setListeners();
    }

    private void setListeners(){
        findViewById(R.id.btn_add_phones).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsPresenter.openAddPhonesActivity();
            }
        });

        findViewById(R.id.btn_add_emails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsPresenter.openAddEmailActivity();
            }
        });

        findViewById(R.id.btn_bluetooth_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsPresenter.openBluetoothOptionsActivity();
            }
        });

        findViewById(R.id.btn_pin_option).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsPresenter.openPinActivity();
            }
        });


        findViewById(R.id.btn_video_duration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsPresenter.openVideoDurationActivity();
            }
        });
    }
}
