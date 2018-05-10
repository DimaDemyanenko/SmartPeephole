package com.example.lucky.alarmsystemfull.Helpers;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.lucky.alarmsystemfull.App.App;
import com.example.lucky.alarmsystemfull.App.Constants;
import com.example.lucky.alarmsystemfull.Helpers.Email.GMailSender;
import com.example.lucky.alarmsystemfull.R;

import java.io.File;


/**
 * Created by Lucky on 10.02.2018.
 */

public class NetworkHelper {

    public static boolean isInternetOn() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) App.getContext().getSystemService(App.getContext().CONNECTIVITY_SERVICE);
        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    public static void sendEmail(File video, String smsText) {
        SendingEmailTask s = new SendingEmailTask();
        s.doInBackground(smsText, video.getAbsolutePath());
    }

    static class SendingEmailTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String videoAbsolutePath = strings[1];

            try {
                String email = SharedPreferenceHelper.getEmail(App.getContext());
                String password = SharedPreferenceHelper.getEmailPassword(App.getContext());

                GMailSender sender = new GMailSender(email, password);

                sender.addAttachment(videoAbsolutePath,
                        videoAbsolutePath.substring(videoAbsolutePath.indexOf("AlarmSystem") + 12));

                sender.sendMail(AppHelper.getStringForResources(R.string.email_title),
                        strings[0],
                        email,
                        email);

                Log.d(Constants.TAG_EMAIL, "Отправлено письмо - " + strings[0]);
            } catch (Exception e) {
                Log.d(Constants.TAG_EMAIL, "Error email");
            }
            return null;
        }
    }


}
