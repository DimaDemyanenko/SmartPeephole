package com.example.lucky.alarmsystemfull.Views;

/**
 * Created by Lucky on 26.02.2018.
 */

public interface OptionEmailView extends StartActivityInterface {

    void btnNextSetEnable(boolean state);
    void firstStart(String email, String password);
    void setBtnNextVisibility(boolean state);
}
