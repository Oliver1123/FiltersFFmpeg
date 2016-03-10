package com.example.oliver.filtersffmpeg;

import android.app.Application;
import android.util.Log;

import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;

/**
 * Created by oliver on 10.03.16.
 */
public class MainApp extends Application {

    private static final String TAG = "MainApp";

    @Override
    public void onCreate() {
        super.onCreate();
//        File ffmpegfile =new File(getApplicationContext().getFilesDir().getAbsolutePath() + File.separator + "ffmpeg");
//        if (ffmpegfile.exists()) {
//            Log.d(TAG, "ffmpeg exist delete " + ffmpegfile.delete());
//        }else {
//            Log.d(TAG, "ffmpeg !exist ");
//        }
        FFmpeg ffmpeg = FFmpeg.getInstance(getApplicationContext());
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Log.d(TAG, " ffmpeg loadBinary onStart");
                }

                @Override
                public void onFailure() {
                    Log.d(TAG, " ffmpeg loadBinary onFailure");
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG, " ffmpeg loadBinary onSuccess");
                }

                @Override
                public void onFinish() {
                    Log.d(TAG, " ffmpeg loadBinary onFinish");
                }
            });
        } catch (FFmpegNotSupportedException e) {
//             Handle if FFmpeg is not supported by device
            Log.d(TAG, " ffmpeg loadBinary exception " + e.getMessage());
        }
    }

}
