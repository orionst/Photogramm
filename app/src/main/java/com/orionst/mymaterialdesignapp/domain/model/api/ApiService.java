package com.orionst.mymaterialdesignapp.domain.model.api;

import com.orionst.mymaterialdesignapp.domain.model.entity.netstore.QueryResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/?image_type=photo")
    Observable<QueryResult> getImages(@Query("key") String accessToken);

}
