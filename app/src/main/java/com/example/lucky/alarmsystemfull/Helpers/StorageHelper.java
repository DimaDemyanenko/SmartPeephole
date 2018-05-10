package com.example.lucky.alarmsystemfull.Helpers;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.example.lucky.alarmsystemfull.App.Constants;

/**
 * Created by Lucky on 21.02.2018.
 */

public class StorageHelper {

    public static long getFreeSizeInternalStorage() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } else {
            bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        }
        long megAvailable = bytesAvailable / (1024 * 1024);
        Log.d(Constants.TAG_DEBUG, "Available MB : " + megAvailable);
        return megAvailable;
    }
}
