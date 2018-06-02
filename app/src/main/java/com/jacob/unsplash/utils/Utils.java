package com.jacob.unsplash.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jacob.unsplash.model.Photo;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(extStorageState);
    }

    public static boolean isResolved(Intent intent, Context activity) {
        return intent.resolveActivity(activity.getPackageManager()) != null;
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getWindow().getDecorView().getRootView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private static void closeQuietly(Closeable outputStream) {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr == null) return false;
            NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void savePhoto(Photo photo, Callback callback) {
        File outputFile = createOutputFile();
        boolean result = writeBitmapTo(photo.getBitmap(), outputFile);

        if (result) {
            callback.onSaveSuccess(outputFile);
        } else {
            callback.onSaveFail();
        }
    }

    private static boolean writeBitmapTo(Bitmap bitmap, File outputFile) {
        if (isExternalStorageAvailable() && !isExternalStorageReadOnly()) {
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                closeQuietly(outStream);
            }
        } else {
            Log.i(LOG_TAG, "Couldn't write log. Read-only file system.");
            return false;
        }
    }

    private static File createOutputFile() {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/UnSplash");
        if (!dir.mkdirs()) {
            Log.e(LOG_TAG, "Could not create file" + dir.getAbsolutePath());
        }
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        return new File(dir, fileName);
    }

    public static void notifyMediaScanner(File outputFile, AppCompatActivity activity) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(outputFile));
        activity.sendBroadcast(intent);
    }

    public static void addFragment(AppCompatActivity activity, final Fragment fragment, int container_id) {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(container_id, fragment)
                .commit();
    }

    public interface Callback {
        void onSaveSuccess(File file);

        void onSaveFail();
    }
}
