package com.example.lucky.alarmsystemfull.Activities.FirstStart;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.Presenters.NeedBluetoothPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.NeedBluetoothView;

public class NeedChooseBluetoothActivity extends MvpAppCompatActivity implements NeedBluetoothView {

    @InjectPresenter
    NeedBluetoothPresenter needBluetoothPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_choose_bluetooth);

        findViewById(R.id.btn_choose_bluetooth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                needBluetoothPresenter.openActivity();
                finish();
            }
        });
    }
}
