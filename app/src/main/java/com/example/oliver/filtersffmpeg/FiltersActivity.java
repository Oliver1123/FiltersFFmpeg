package com.example.oliver.filtersffmpeg;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

import java.io.File;

public class FiltersActivity extends AppCompatActivity {

    private final String IN_FILE_NAME = "result";
    private final String OUT_FILE_NAME = "output";
    private final String IN_FILE = IN_FILE_NAME + ".mp4";
    private final String OUT_FILE = OUT_FILE_NAME+ ".mp4";
    private static final String TAG = "FiltersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        FFmpeg ffmpeg   = FFmpeg.getInstance(this);

        try {
            String cmd = generateSepiaCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateInverseCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateSplitCommand(getInputFile(), null); // work
//            String cmd = generateVideoCommand(getFileDir(), getFileDir() + "/result.mp4");
//            String cmd = "ffmpeg -filters";
            Log.d(TAG, "cmd : " + cmd);
            ffmpeg.execute(cmd.split(" "), new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {
                    Log.d(TAG, "ffmpeg onStart");
                }

                @Override
                public void onProgress(String message) {
                    Log.d(TAG, "ffmpeg onPRogress " + message);
                }

                @Override
                public void onFailure(String message) {
                    Log.d(TAG, "ffmpeg onFailure " + message);
                }

                @Override
                public void onSuccess(String message) {
                    Log.d(TAG, "ffmpeg onSuccess " + message);
                }

                @Override
                public void onFinish() {
                    Log.d(TAG, "ffmpeg onFinish");
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Log.d(TAG, "ffmpeg exception "  + e.getMessage());
        }
    }

    private String generataOUtFileName() {
        return "out_" + String.valueOf(System.currentTimeMillis());
    }

    private String generateSepiaCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -filter_complex " +
                "colorchannelmixer=.3:.4:.3:0:.3:.4:.3:0:.3:.4:.3 " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateInverseCommand(String inputFile, String outputFile) {
        String cmd = "-r 24 -i " +
                inputFile +
                " -vf lutrgb=r=negval:g=negval:b=negval " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateSplitCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf fps=24 " + getFileDir() + "/image_%03d.png";
        return cmd;
    }

    private String generateVideoCommand(String dir, String outputFile) {
        String cmd = "-i " +
                dir +"/image_%03d.png -c:v libx264 -pix_fmt yuv420p " + outputFile;
        return cmd;
    }


    private String getInputFile() {
//        File soloDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "SOLO");
//        return new File(soloDir, IN_FILE).getAbsolutePath();
        return new File(getSoloFilesDir(), IN_FILE).getAbsolutePath();
    }
    private String getFileDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }
    private String getSoloFilesDir() {
        return new File(getFileDir(), "SOLO").getAbsolutePath();
    }
}
