package com.example.lucky.alarmsystemfull.Helpers;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by Lucky on 10.02.2018.
 */

public class FileHelper {

    private static final String DIRECTORY = "AlarmSystem";

    public static File getFileName() {
        File pictureFileDir = getDir();

        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
            return null;
        }

        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(timeInMillis);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss a");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");

        String date = dateFormat.format(cal1.getTime());
        String namePhoto = date;
        String photoFile = namePhoto + ".mp4";
        String photoFilename = pictureFileDir.getPath() + File.separator + photoFile;
        File pictureFile = new File(photoFilename);

        return pictureFile;
    }

    public static ArrayList<String> getFileList() {
        ArrayList<String> allVideo = new ArrayList<>();

        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        new File(sdDir, DIRECTORY);

        String path = new File(sdDir, DIRECTORY).getPath();
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            allVideo.add(files[i].getAbsolutePath());
        }
        return allVideo;
    }

    // этот метод будет получать путь к папке где нужно сохранить фото
    private static File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, DIRECTORY);
    }

    /**
     * @return sorted all video files
     */
    public static ArrayList<String> loadAllFiles() {
        ArrayList<String> allVideos = FileHelper.getFileList();
        Collections.sort(allVideos);
        return allVideos;
    }

    /**
     * Get first file (old video) and delete
     */
    public static void deleteFile() {
        File file = new File(loadAllFiles().get(0));
        file.delete();
    }
}
