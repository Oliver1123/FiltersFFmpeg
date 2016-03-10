package com.example.oliver.filtersffmpeg;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;

import java.io.File;
import java.io.FilenameFilter;

public class FiltersActivity extends AppCompatActivity implements View.OnClickListener {

    private final String IN_FILE_NAME = "solo_in_000";
    private final String OUT_FILE_NAME = "output";
    private final String IN_FILE = IN_FILE_NAME + ".mp4";
    private final String OUT_FILE = OUT_FILE_NAME+ ".mp4";
    private static final String TAG = "FiltersActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        initShareButtons();
        FFmpeg ffmpeg   = FFmpeg.getInstance(this);

        try {
//            String cmd = generateSepiaCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateBlurCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateColorBalanceCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateCropCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateCurvesCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateDrawGridCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateEdgeDetectCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateGeqCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateInverseCommand(getInputFile(), getInputFile().replace(IN_FILE_NAME, generataOUtFileName()));
//            String cmd = generateSplitCommand(getInputFile(), null); // work
//            String cmd = generateVideoCommand(getFileDir(), getFileDir() + "/result.mp4");
//            String cmd = "ffmpeg -filters";
            String cmd = generateSplitCommand(getInputFile(), null);
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
                    Log.d(TAG, "last frame: " + getLastFrame(getFileDir(), "image_"));
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

    private String getLastFrame(String fileDir, final String filePrefix) {
        File fileDirectory = new File(fileDir);
        File[] frames = fileDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (filename.startsWith(filePrefix))
                    return true;
                return false;
            }
        });
        for (int i = 0; i < frames.length - 1; i++) {
            frames[i].delete();
        }
        return frames[frames.length - 1].getAbsolutePath();
    }

    private void initShareButtons() {
        findViewById(R.id.btnShareFacebook).setOnClickListener(this);
        findViewById(R.id.btnShareInstagram).setOnClickListener(this);
        findViewById(R.id.btnShareWatsApp).setOnClickListener(this);
        findViewById(R.id.btnShareMessenger).setOnClickListener(this);
        findViewById(R.id.btnShareTwitter).setOnClickListener(this);
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

    private String generateBlurCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -filter_complex " +
                "boxblur=luma_radius=2:luma_power=5 " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateColorBalanceCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf colorbalance=rs=.5:bh=-0.5 " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateCropCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf " +
                "crop=320:320:0:0 " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateCurvesCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf " +
                "curves=vintage " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateDrawGridCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf " +
                "drawgrid=width=100:height=100:thickness=2:color=red@0.5 " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateEdgeDetectCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf " +
                "edgedetect=low=0.1:high=0.4 " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateGeqCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf " +
                "geq=r='X/W*r(X,Y)':g='(1-X/W)*g(X,Y)':b='(H-Y)/H*b(X,Y)' " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateRotateCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf " +
                "rotate=PI/6 " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }

    private String generateFadeCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf " +
                "fade=in:20:80 " +
                "-c:v libx264 -c:a copy -pix_fmt yuv420p " +
                outputFile;
        return cmd;
    }
    private String generateElbgCommand(String inputFile, String outputFile) {
        String cmd = "-i " +
                inputFile +
                " -vf " +
                "elbg=codebook_length=250 " +
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
//
//    private String generateGetFramesCommand(String inputFile) {
//        String cmd = "-sseof 0 -i " +
//                inputFile +
//                " -frames:v 1 out1.jpg";
////        ffmpeg -ss 0.5 -i inputfile.mp4 -t 1 -s 480x300 -f image2 imagefile.jpg
//        return cmd;
//    }


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShareFacebook:
               shareIntent("com.facebook.katana");
                break;
            case R.id.btnShareInstagram:
                shareIntent("com.instagram.android");
                break;
            case R.id.btnShareWatsApp:
                shareIntent("com.whatsapp");
                break;
            case R.id.btnShareMessenger:
                shareIntent("com.facebook.orca");
                break;
            case R.id.btnShareTwitter:
                shareIntent("com.twitter.android");
                break;
        }
    }

    private void shareIntent(String packageName) {

        if (isPackageInstalled(packageName, FiltersActivity.this)) {
            String type = "video/*";
            // Create the new Intent using the 'Send' action.
            Intent share = new Intent(Intent.ACTION_SEND);

            // Set the MIME type
            share.setType(type);

            // Create the URI from the media
            Uri uri = getInputFileUri();

            // Add the URI to the Intent.
            share.putExtra(Intent.EXTRA_STREAM, uri);

            // Broadcast the Intent.
            share.setPackage(packageName);
            startActivity(Intent.createChooser(share, "Share to"));
        } else {
            goToMarket(packageName);
        }
    }

    @NonNull
    private Uri getInputFileUri() {
        return Uri.fromFile(new File(getInputFile()));
    }

    private void goToMarket(String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=" + packageName + "&hl=en"));
        startActivity(intent);
    }
    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
