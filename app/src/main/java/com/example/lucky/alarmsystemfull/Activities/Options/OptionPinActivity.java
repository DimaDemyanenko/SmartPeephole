package com.example.lucky.alarmsystemfull.Activities.Options;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Presenters.OptionPinPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.OptionPinView;

public class OptionPinActivity extends MvpAppCompatActivity implements OptionPinView {

    @InjectPresenter
    OptionPinPresenter optionPinPresenter;

    private EditText pin;
    private EditText pinRepeat;
    private Button btnAdd;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        initViews();
        optionPinPresenter.checkFirstStart(getIntent()
                .getBooleanExtra(Constants.FIRST_START, false));
    }

    private void initViews(){
        pin = findViewById(R.id.new_phone);
        pinRepeat = findViewById(R.id.password);
        btnAdd = findViewById(R.id.btn_add);
        btnNext = findViewById(R.id.btn_next);
        setListeners();
        setBtnNextEnable(false);
    }

    private void setListeners(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pin.getText().toString().isEmpty()) {
                    AppHelper.showMessage(AppHelper.getStringForResources(R.string.pin_is_right));
                } else if (!pin.getText().toString().equals(pinRepeat.getText().toString())) {
                    AppHelper.showMessage(AppHelper.getStringForResources(R.string.pin_is_right_again));
                } else {
                    AppHelper.showMessage(AppHelper.getStringForResources(R.string.pin_saved));
                    optionPinPresenter.savePin( pin.getText().toString());
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionPinPresenter.openActivity();
                finish();
            }
        });
    }

    @Override
    public void setBtnNextEnable(boolean state) {
        btnNext.setEnabled(state);
    }

    @Override
    public void setBtnNextVisibility(boolean state) {
        btnNext.setVisibility(state ? View.VISIBLE : View.GONE);
    }
}
