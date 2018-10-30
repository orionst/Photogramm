package com.orionst.mymaterialdesignapp.repositories;

import android.net.Uri;

import com.orionst.mymaterialdesignapp.domain.model.api.ApiService;
import com.orionst.mymaterialdesignapp.domain.model.entity.Image;
import com.orionst.mymaterialdesignapp.domain.model.entity.netstore.NetImage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class OnlineRepository {

    private ApiService api;

    public OnlineRepository(ApiService api) {
        this.api = api;
    }

    public Observable<List<Image>> getQueryResult(String accessToken) {
        return api.getImages(accessToken)
                .subscribeOn(Schedulers.io())
                .map(queryResult -> {
                    List<Image> arrList = new ArrayList<>();
                    for (NetImage netImage: queryResult.getImages()) {
                        arrList.add(new Image(Uri.parse(netImage.getWebformatURL()), false, true));
                    }
                    return arrList;
                });
    }
}
