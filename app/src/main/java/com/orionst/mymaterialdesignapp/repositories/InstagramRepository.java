package com.orionst.mymaterialdesignapp.repositories;

import com.orionst.mymaterialdesignapp.domain.model.api.ApiService;
import com.orionst.mymaterialdesignapp.domain.model.entity.Image;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class InstagramRepository {

    private ApiService api;

    public InstagramRepository(ApiService api) {
        this.api = api;
    }

    public Observable<String> getToken(String username) {
        return api.getToken(username)
                .subscribeOn(Schedulers.io());
    }

    public Observable<List<Image>> getImages(String accessToken) {
        return api.getImages(accessToken)
                .subscribeOn(Schedulers.io());
    }
}
