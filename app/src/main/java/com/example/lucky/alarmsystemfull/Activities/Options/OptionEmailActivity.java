package com.example.lucky.alarmsystemfull.Activities.Options;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Presenters.OptionEmailPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.OptionEmailView;

public class OptionEmailActivity extends MvpAppCompatActivity implements OptionEmailView {

    @InjectPresenter
    OptionEmailPresenter optionEmailPresenter;

    private EditText eTxtEmail;
    private EditText eTxtPassword;
    private Button btnAdd;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        initViews();
    }

    private void initViews() {
        eTxtPassword = findViewById(R.id.password);
        eTxtEmail = findViewById(R.id.new_phone);
        btnAdd = findViewById(R.id.btn_add);
        btnNext = findViewById(R.id.btn_next);

        setListeners();

        optionEmailPresenter.setBtnNextEnable(false);
        optionEmailPresenter.checkFirstStart(getIntent()
                .getBooleanExtra(Constants.FIRST_START, false));
    }

    private void setListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionEmailPresenter.checkValidEmail(eTxtEmail.getText().toString(),
                        eTxtPassword.getText().toString());

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionEmailPresenter.openActivity();
                finish();
            }
        });
    }

    @Override
    public void btnNextSetEnable(boolean state) {
        btnNext.setEnabled(state);
    }

    @Override
    public void firstStart(String email, String password) {
        eTxtEmail.setText(email);
        eTxtPassword.setText(password);
    }

    @Override
    public void setBtnNextVisibility(boolean state) {
        btnNext.setVisibility(state ? View.VISIBLE : View.GONE);
    }
}
