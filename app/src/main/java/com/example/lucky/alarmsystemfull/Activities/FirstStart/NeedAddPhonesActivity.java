package com.example.lucky.alarmsystemfull.Activities.FirstStart;

import android.os.Bundle;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.Presenters.NeedAddPhonesPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.NeedAddPhonesView;

public class NeedAddPhonesActivity extends MvpAppCompatActivity implements NeedAddPhonesView {

    @InjectPresenter
    NeedAddPhonesPresenter needAddPhonesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_add_phones);

        findViewById(R.id.btn_add_phones).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                needAddPhonesPresenter.openAddPhoneActivity();
                finish();
            }
        });
    }
}
