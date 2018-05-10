package com.example.lucky.alarmsystemfull.Activities.FirstStart;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.Presenters.NeedPinPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.NeedPinView;

public class NeedPinActivityActivity extends MvpAppCompatActivity implements NeedPinView {

    @InjectPresenter
    NeedPinPresenter needPinPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_pin_activity);

        findViewById(R.id.btn_choose_bluetooth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                needPinPresenter.openActivity();
                finish();
            }
        });
    }
}
