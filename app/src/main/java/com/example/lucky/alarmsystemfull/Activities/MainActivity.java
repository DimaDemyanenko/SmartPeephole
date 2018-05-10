package com.example.lucky.alarmsystemfull.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionsActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Presenters.MainPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.MainView;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    private Button btnStart;
    private Button btnOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mainPresenter.checkPermissions(this);
    }

    private void initViews(){
        btnStart = findViewById(R.id.btn_start);
        btnOptions = findViewById(R.id.btn_options);
        setListeners();
    }

    private void setListeners(){
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.openActivity();
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.openOptions();
            }
        });
    }


}
