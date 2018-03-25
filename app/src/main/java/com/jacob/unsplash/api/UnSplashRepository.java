package com.jacob.unsplash.api;

import android.os.Handler;
import android.os.Looper;

import com.jacob.unsplash.model.Result;
import com.jacob.unsplash.network.CacheControlInterceptor;
import com.jacob.unsplash.MyApp;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.model.ResponseModel;
import com.jacob.unsplash.network.HeaderInterceptor;
import com.jacob.unsplash.utils.ExecutorHelper;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by vynnykiakiv on 3/24/18.
 */

public class UnSplashRepository {
    private static final String BASE_URL = "https://api.unsplash.com";
    private static UnSplashRepository sUnSplashRepository;
    private static File sCacheDir = new File(MyApp.getContext().getCacheDir(), "cache");
    private static int sCacheSize = 5 * 1024 * 1024;
    private static Cache sCache = new Cache(sCacheDir, sCacheSize);
    private static OkHttpClient sClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new CacheControlInterceptor())
            .addInterceptor(new CacheControlInterceptor())
            .addInterceptor(new HeaderInterceptor())
            .cache(sCache)
            .build();
    private Listener mListener;
    private Handler mHandler = new Handler((Looper.getMainLooper()));

    public static UnSplashRepository getInstance() {
        synchronized (UnSplashRepository.class) {
            if (sUnSplashRepository == null) {
                sUnSplashRepository = new UnSplashRepository();
            }
        }
        return sUnSplashRepository;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void search(final String searchExp) {
        ExecutorHelper.submit(new Runnable() {
            @Override
            public void run() {
                searchFor(searchExp);
            }
        });
    }

    private void searchFor(String searchExp) {
        try {
            Call<ResponseModel> call = getService().search(1, 50, searchExp);
            Response<ResponseModel> response = call.execute();

            if (response.code() == HttpURLConnection.HTTP_OK) {
                List<Photo> photos = getPhotos(response.body());
                onSuccess(photos);
            } else {
                onFail(response.message() + "response code " + response.code());
            }
        } catch (IOException e) {
            onFail(e.getMessage());
        }
    }

    private List<Photo> getPhotos(ResponseModel responseModel) {
        List<Photo> photoList = new ArrayList<Photo>();
        for (Result result : responseModel.getResults()) {
            photoList.add(result.getPhotos());
        }
        return photoList;
    }

    private UnSplashService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(sClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return retrofit.create(UnSplashService.class);
    }

    private void onFail(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onFail(message);
                }
            }
        });
    }

    private void onSuccess(final List<Photo> photos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onSuccess(photos);
                }
            }
        });
    }

    private void runOnUiThread(Runnable runnable) {
        if (mHandler != null) {
            mHandler.post(runnable);
        }
    }

    public interface Listener {
        void onSuccess(List<Photo> photos);

        void onFail(String message);
    }
}
