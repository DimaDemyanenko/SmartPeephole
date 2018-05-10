package com.example.lucky.alarmsystemfull.Presenters;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.AppHelper;
import com.example.lucky.alarmsystemfull.Helpers.FileHelper;
import com.example.lucky.alarmsystemfull.Helpers.NetworkHelper;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;
import com.example.lucky.alarmsystemfull.Helpers.StorageHelper;
import com.example.lucky.alarmsystemfull.Models.SensorsLogic;
import com.example.lucky.alarmsystemfull.R;
import com.example.lucky.alarmsystemfull.Views.myCameraView;
import com.otaliastudios.cameraview.SessionType;
import com.otaliastudios.cameraview.Size;
import com.otaliastudios.cameraview.VideoQuality;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper.CONNECTING_STATUS;
import static com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper.MESSAGE_READ;
import static com.example.lucky.alarmsystemfull.Helpers.BluetoothHelper.REQUEST_ENABLE_BT;

/**
 * Created by Lucky on 27.02.2018.
 */

@InjectViewState
public class CameraPresenter extends MvpPresenter<myCameraView> implements SensorsLogic.onClick {

    private static final long MINIMAL_FREE_STORAGE = 500;

    private boolean mCapturingVideo;
    private boolean isDoing;

    private String bluetoothAddress;
    private String bluetoothName;
    private long duration;

    private SensorsLogic sensorLogic;

    private BluetoothAdapter mBTAdapter;
    private Handler mHandlerBluetooth; // Our main handler that will receive callback notifications
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data


    public CameraPresenter() {
        sensorLogic = new SensorsLogic(this);
        duration = SharedPreferenceHelper.getVideoDuration(App.getContext());
        bluetoothAddress = SharedPreferenceHelper.getBluetoothMacAddress(App.getContext());
        bluetoothName = SharedPreferenceHelper.getBluetoothName(App.getContext());
    }

    public void initCamera() {
        getViewState().initCamera(SessionType.VIDEO, VideoQuality.LOWEST);
        getViewState().setButtonsVisibility(false);
    }

    public void pauseWork() {
        /**
         * останавливаем текущую запись, и сразу запускаем опять камеру
         * чтоб не остановилось изображение на экране
         */
        getViewState().restartCamera();
        isDoing = false;
        getViewState().setStatusViewVisibility(true);
    }

    public void startWork() {
        isDoing = true;
        mCapturingVideo = false;
        getViewState().setStatusViewVisibility(false);
        captureVideo(duration);
    }

    public void newCycle() {
        if (isDoing) {
            mCapturingVideo = false;
            captureVideo(duration);
        }
    }

    private void captureVideo(long duration) {
        if (mCapturingVideo) return;
        mCapturingVideo = true;
        Log.d(Constants.TAG_DEBUG, "Recording for " + String.valueOf(duration / 1000 - 1) + " seconds...");
        getViewState().startCapturingVideo(FileHelper.getFileName(), duration);
    }

    public void checkSensors(File file) {
        sensorLogic.checkSensors(file);
//        sensors.checkSensors(file, isCall, isKnock, isGercon, sendFile, this);
    }

    public boolean getStatus() {
        return isDoing;
    }

    public void setStatusViewVisibility() {
        getViewState().setStatusViewVisibility(getStatus());
    }

    public void setStatusViewText(String text) {
        getViewState().setStatusViewText(text);
    }

    public void checkFreeSizeStorage() {
        if (StorageHelper.getFreeSizeInternalStorage() < MINIMAL_FREE_STORAGE) {
            FileHelper.deleteFile();
        }
    }

    public void checkInternetConnection(){
        if(NetworkHelper.isInternetOn()){
            Log.d(Constants.TAG_DEBUG, "All right");
        } else {
            Log.d(Constants.TAG_DEBUG, "Not connection");
        }
    }


    /*
    BLUETOOTH
     */

    public static boolean setBluetooth(boolean enable) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            return bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            return bluetoothAdapter.disable();
        }
        // No need to change bluetooth state
        return true;
    }


    private void bluetoothOn(Activity activity) {
        if (!mBTAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            AppHelper.showMessage("Bluetooth turned on");
            AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_on));
        } else {
            AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_already_on));
        }
    }

    public void bluetoothOff() {
        mBTAdapter.disable(); // turn off
        AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_off));
    }

    public void bluetoothInit() {
        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio
        if (mBTAdapter.isEnabled()) {
            bluetoothConnection();
        } else {
            setBluetooth(true);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bluetoothConnection();
//            bluetoothOn(activity);
        }
    }

    public void bluetoothConnection() {
        mHandlerBluetooth = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == MESSAGE_READ) {
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    if (isDoing) {
//                        sensors.sensorDetect(readMessage.substring(0, 1));
                        sensorLogic.sensorDetect(readMessage.substring(0, 1));
                    }
                    Log.d(Constants.TAG_DEBUG, "Получил команду " + readMessage);
                }

                if (msg.what == CONNECTING_STATUS) {
                    if (msg.arg1 == 1) {
                        AppHelper.showMessage(AppHelper
                                .getStringForResources(R.string.bluetooth_connected) + (String) (msg.obj));
                        getViewState().setButtonsVisibility(true);
//                        startWork();
                        getViewState().showStartWorkToast();
                    } else {
                        AppHelper.showMessage(AppHelper.getStringForResources(R.string.bluetooth_error_connecting));
                    }
                }
            }
        };


        new Thread() {
            public void run() {
                boolean fail = false;

                BluetoothDevice device = mBTAdapter.getRemoteDevice(bluetoothAddress);

                try {
                    mBTSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    AppHelper.showMessage(AppHelper
                            .getStringForResources(R.string.bluetooth_socket_creation_failed));
                }
                // Establish the Bluetooth socket connection.
                try {
                    mBTSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        mBTSocket.close();
                        mHandlerBluetooth.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        AppHelper.showMessage(AppHelper
                                .getStringForResources(R.string.bluetooth_socket_creation_failed));
                    }
                }
                if (fail == false) {
                    mConnectedThread = new ConnectedThread(mBTSocket);
                    mConnectedThread.start();

                    mHandlerBluetooth.obtainMessage(CONNECTING_STATUS, 1, -1, bluetoothName)
                            .sendToTarget();
                }
            }
        }.start();
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BTMODULEUUID);
        } catch (Exception e) {
            Log.e(Constants.TAG_DEBUG, "Could not create Insecure RFComm Connection", e);
        }
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void stopCamera() {
//        getViewState().stopCamera();
//        pauseWork();
        getViewState().startAlarmActivity();
    }

    public void disconnectBluetooth(){
        mConnectedThread.cancel();
        mConnectedThread = null;
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        buffer = new byte[1024];
                        SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        mHandlerBluetooth.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.d("ddd", e.getMessage());
            }
        }
    }
}
