package com.example.lucky.alarmsystemfull.Activities.Options;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper;
import com.example.lucky.alarmsystemfull.Presenters.OptionBluetoothPresenter;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.OptionBluetoothView;

public class OptionBluetoothActivity extends MvpAppCompatActivity implements OptionBluetoothView {

    @InjectPresenter
    OptionBluetoothPresenter optionBluetoothPresenter;

    private TextView bluetoothStatus;
    private Button scanBtn;
    private Button discoverBtn;
    private ListView devicesListView;
    private ArrayAdapter<String> bTArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_option);

        optionBluetoothPresenter.setFirstStartFlag(
                getIntent().getBooleanExtra(Constants.FIRST_START, false));

        initViews();

        optionBluetoothPresenter.checkPermission(this);
    }

    private void initViews() {
        bluetoothStatus = (TextView) findViewById(R.id.bluetoothStatus);
        scanBtn = (Button) findViewById(R.id.scan);
        discoverBtn = (Button) findViewById(R.id.discover);

        initArrayList();
    }

    private void initArrayList() {
        bTArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);

        devicesListView = (ListView) findViewById(R.id.devicesListView);
        devicesListView.setAdapter(bTArrayAdapter);
        devicesListView.setOnItemClickListener(optionBluetoothPresenter.getListener());

        if (bTArrayAdapter == null) {
            bluetoothStatus.setText(R.string.bluetooth_status);
            AppHelper.showMessage(getResources().getString(R.string.bluetooth_status));
        } else {
            scanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionBluetoothPresenter.bluetoothOn(OptionBluetoothActivity.this);
                }
            });

            discoverBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionBluetoothPresenter.discover();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        if (requestCode == BluetoothHelper.REQUEST_ENABLE_BT) {
            optionBluetoothPresenter.setBluetoothStatus(resultCode == RESULT_OK ?
                    getResources().getString(R.string.bluetooth_on) :
                    getResources().getString(R.string.bluetooth_off));
        }
    }

    @Override
    public void setBluetoothStatus(String text) {
        bluetoothStatus.setText(text);
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void addNewBluetoothDevice(BluetoothDevice device) {
        bTArrayAdapter.add(device.getName() + "\n" + device.getAddress());
        bTArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void btArrayAdapterClear() {
        bTArrayAdapter.clear();
    }
}
