package com.example.lucky.alarmsystemfull.Activities.FirstStart;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.Presenters.NeedAddEmailPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.NeedAddEmailView;

public class NeedAddEmailActivity extends MvpAppCompatActivity implements NeedAddEmailView {

    @InjectPresenter
    NeedAddEmailPresenter needAddEmailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_add_email);

        findViewById(R.id.btn_add_email).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                needAddEmailPresenter.openAddEmailActivity();
                finish();
            }
        });
    }
}