package com.orionst.mymaterialdesignapp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PhotoView extends MvpView {

    void onPhotoView(String uriString);

    void onNewImageList();

    void showNotification(String message);

    void sendReloadListMessage();
}
