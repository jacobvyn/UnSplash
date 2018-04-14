package com.jacob.unsplash.api;

import com.jacob.unsplash.model.Result;
import com.jacob.unsplash.network.CacheControlInterceptor;
import com.jacob.unsplash.App;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.model.ResponseModel;
import com.jacob.unsplash.network.HeaderInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;


/**
 * Created by vynnykiakiv on 3/24/18.
 */

public class UnSplashRepository {
    private static final String BASE_URL = "https://api.unsplash.com";
    private static UnSplashRepository sUnSplashRepository;
    private static File sCacheDir = new File(App.getContext().getCacheDir(), "cache");
    private static int sCacheSize = 5 * 1024 * 1024;
    private static Cache sCache = new Cache(sCacheDir, sCacheSize);
    private static OkHttpClient sClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new CacheControlInterceptor())
            .addInterceptor(new CacheControlInterceptor())
            .addInterceptor(new HeaderInterceptor())
            .cache(sCache)
            .build();

    public static UnSplashRepository getInstance() {
        synchronized (UnSplashRepository.class) {
            if (sUnSplashRepository == null) {
                sUnSplashRepository = new UnSplashRepository();
            }
        }
        return sUnSplashRepository;
    }


    public Observable<List<Photo>> search(String searchExp) {
        Observable<ResponseModel> call = getService().search(1, 50, searchExp);
        return call.map(new Function<ResponseModel, List<Photo>>() {
            @Override
            public List<Photo> apply(ResponseModel responseModel) throws Exception {
                return getPhotos(responseModel);
            }
        });
    }

    public static List<Photo> getPhotos(ResponseModel responseModel) {
        List<Photo> photoList = new ArrayList<Photo>();
        for (Result result : responseModel.getResults()) {
            photoList.add(result.getPhotos());
        }
        return photoList;
    }

    private UnSplashService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(sClient)
                .build();
        return retrofit.create(UnSplashService.class);
    }

}
