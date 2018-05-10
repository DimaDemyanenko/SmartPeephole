package com.example.lucky.alarmsystemfull.Views;

import android.content.Intent;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Lucky on 25.02.2018.
 */

public interface StartActivityInterface extends MvpView {

    void startActivity(Intent intent);
}
