package com.example.lucky.alarmsystemfull.Presenters;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.CameraActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedAddEmailActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedPinActivityActivity;
import com.example.lucky.alarmsystemfull.Activities.Options.OptionBluetoothActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.DataHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.Views.OptionPhoneListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucky on 25.02.2018.
 */

@InjectViewState
public class OptionPhoneListPresenter extends MvpPresenter<OptionPhoneListView> {

    private List<String> phones = new ArrayList<>();
    private String macAddress;
    private String email;
    private String pin;

    public OptionPhoneListPresenter(){
        phones = DataHelper.loadPhonesFromSharedPreference(App.getContext());
        pin = SharedPreferenceHelper.getPin(App.getContext());
        email = SharedPreferenceHelper.getEmail(App.getContext());
        macAddress = SharedPreferenceHelper.getBluetoothMacAddress(App.getContext());
    }

    public List<String> getPhones(){
        return phones;
    }

    public int getPhonesLastPosition(){
        return phones.size() - 1;
    }

    public void addPhone(String phone){
        phones.add(phone);
        DataHelper.savePhonesToSharedPreference(App.getContext(), phones);
        getViewState().notifyItemInserted(getPhonesLastPosition());
    }

    public void next(){
        if(email.isEmpty()) {
            getViewState().startActivity( new Intent(App.getContext(), NeedAddEmailActivity.class));
        } else if(macAddress.isEmpty()) {
            getViewState().startActivity( new Intent(App.getContext(), OptionBluetoothActivity.class));
        } else if(pin.isEmpty()){
            getViewState().startActivity( new Intent(App.getContext(), NeedPinActivityActivity.class));
        } else {
            getViewState().startActivity( new Intent(App.getContext(), CameraActivity.class));

        }
    }

    public void removeItemFromAdapter(int position){
        phones.remove(position);
        DataHelper.savePhonesToSharedPreference(App.getContext(), phones);
    }
}
