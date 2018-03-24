package com.jacob.unsplash.api;

import com.jacob.unsplash.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by vynnykiakiv on 3/24/18.
 */

public interface UnSplashService {

    @GET("/search/photos/")
    Call<ResponseModel> search(@Query("page") int page, @Query("per_page") int perPage, @Query("query") String expression);
}
