package com.example.lucky.alarmsystemfull.Presenters;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.Activities.CameraActivity;
import com.example.lucky.alarmsystemfull.Activities.FirstStart.NeedPinActivityActivity;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.OptionBluetoothView;

import java.io.UnsupportedEncodingException;

import static com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper.CONNECTING_STATUS;
import static com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper.MESSAGE_READ;
import static com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper.REQUEST_ENABLE_BT;

/**
 * Created by Lucky on 26.02.2018.
 */

@InjectViewState
public class OptionBluetoothPresenter extends MvpPresenter<OptionBluetoothView> {

    private boolean isFirstStart;

    private String address;
    private String pin;

    private BluetoothAdapter mBTAdapter;

    public OptionBluetoothPresenter() {
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        startHandler();
    }

    public void setFirstStartFlag(boolean isFirstStart) {
        this.isFirstStart = isFirstStart;
    }

    private void safeBluetoothDataToSharedPreference(String address, String name) {
        SharedPreferenceHelper.setBluetoothMacAddress(App.getContext(), address);
        SharedPreferenceHelper.setBluetoothName(App.getContext(), name);
    }

    public void checkPermission(Activity activity) {
        // Ask for location permission if not already allowed
        if (ContextCompat.checkSelfPermission(
                App.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED)

            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
    }

    private void startHandler() {
        // Our main handler that will receive callback notifications
        Handler mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
//                    mReadBuffer.setText(readMessage);
                }

                if (msg.what == CONNECTING_STATUS) {
                    getViewState().setBluetoothStatus(msg.arg1 == 1
                            ? AppHelper.getStringForResources(R.string.bluetooth_connecting) + (String) (msg.obj)
                            : AppHelper.getStringForResources(R.string.bluetooth_error_connecting));
                }
            }
        };
    }

    public void bluetoothOn(Activity activity) {;
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_already_on));
        }
    }

    public void discover() {
        if (mBTAdapter.isDiscovering()) {
            mBTAdapter.cancelDiscovery();
            AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_discovery_stopped));
        } else {
            if (mBTAdapter.isEnabled()) {
                getViewState().btArrayAdapterClear();
                mBTAdapter.startDiscovery();
                AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_discovery));

                App.getContext().registerReceiver(blReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            } else {
                AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_stopped));
            }
        }
    }

    final BroadcastReceiver blReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                getViewState().addNewBluetoothDevice(device);
            }
        }
    };

    public void finishActivity() {
        AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_device_choose));
        if (isFirstStart) {
            pin = SharedPreferenceHelper.getPin(App.getContext());
            if (pin.isEmpty()) {
                getViewState().startActivity(new Intent(App.getContext(), NeedPinActivityActivity.class));
            } else {
                getViewState().startActivity(new Intent(App.getContext(), CameraActivity.class));
            }
        }
    }

    public AdapterView.OnItemClickListener getListener() {
        return mDeviceClickListener;
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            if (!mBTAdapter.isEnabled()) {
                AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_not_on));
                return;
            }
            getViewState().setBluetoothStatus(AppHelper.getStringForResources(R.string.bluetooth_connecting));
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            address = info.substring(info.length() - 17);
            final String name = info.substring(0, info.length() - 17);

            safeBluetoothDataToSharedPreference(address, name);

            finishActivity();
            getViewState().closeActivity();
        }
    };

    public void setBluetoothStatus(String text){
        getViewState().setBluetoothStatus(text);
    }
}
