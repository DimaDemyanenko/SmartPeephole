package com.example.lucky.alarmsystemfull.Receivers;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;

import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lucky on 10.02.2018.
 */

public class CallReceiver extends PhonecallReceiver {

    AudioManager audioManager;

    @Override
    protected void onIncomingCallReceived(final Context ctx, String number, Date start) {
        ArrayList<String> phonesList = new ArrayList<>();

        String s = "";

        String phone = SharedPreferenceHelper.getPhones(ctx);
        if (phone != null) {
            String[] videoArray = phone.split(",");
            for (int i = 0; i < videoArray.length; i++) {
                s = videoArray[i].replace("(", "")
                        .replace(")", "").replace("-", "");
                phonesList.add(s.substring(2));
            }
        }

        Log.d(Constants.TAG_DEBUG, "Входящий звонок " + number);

        number = number.substring(2);

        for (int i = 0; i < phonesList.size(); i++) {
            if (phonesList.get(i).equals(number)) {
                Log.d(Constants.TAG_DEBUG, "Номер совпал");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        acceptCall(ctx);
                    }
                }).start();
            }
        }
    }

    private void acceptCall(final Context context) {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
        // send ordered broadcast
        context.sendOrderedBroadcast(intent, null);
        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                setSpeakerMode(context);
            }
        };
        mainHandler.post(myRunnable);

    }

    private void setSpeakerMode(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        for (int i = 0; i < 5; i++) {
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        }
        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(true);
//                MainActivity.shouldTurnSpeakerOn = false;
//                MainActivity.shouldTurnSpeakerOff = true;
            }
        }, 700);
    }


    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {

    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        audioManager = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);

        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(false);
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        //
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        //
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {
        //
    }
}