package com.orionst.mymaterialdesignapp.domain.model.api;

import com.orionst.mymaterialdesignapp.domain.model.entity.Image;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("{/oauth/authorize/?client_id={user_id}&redirect_uri=REDIRECT-URI&response_type=token}")
    Observable<String> getToken(@Path("user_id") String user_id);

    @GET("{/v1/tags/nofilter/media/recent?access_token={access_token}}")
    Observable<List<Image>> getImages(@Path("access_token") String accessToken);

}
