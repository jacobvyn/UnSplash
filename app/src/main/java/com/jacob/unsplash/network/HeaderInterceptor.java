package com.jacob.unsplash.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor implements Interceptor {
    //should be in gradle.properties
    private String ID = "e7960320fd69c735d82e0670997083e4cd6c080bb19e23a83ecd742c37093061";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Client-ID " + ID)
                .addHeader("Accept-Version", "v1")
                .build();
        return chain.proceed(request);
    }
}
