package com.orionst.mymaterialdesignapp.presentation.view;

import android.arch.lifecycle.LiveData;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.orionst.mymaterialdesignapp.database.model.Photo;

import java.util.List;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PhotoView extends MvpView {

    void getImages(LiveData<List<Photo>> allPhotos);
    void onFavoriteChanged(boolean favoriteState);
    void onPhotoDelete(boolean actionSuccesseful);
    void onPhotoView(String uriString);
}
