package com.example.lucky.alarmsystemfull.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Lucky on 10.02.2018.
 */

public class ImageHelper {

    public static byte[] compressPhoto(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitmap = Bitmap.createScaledBitmap(
                bitmap, 600, 800, false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out);
        byte[] byteArray = out.toByteArray();
        return byteArray;
    }
}
