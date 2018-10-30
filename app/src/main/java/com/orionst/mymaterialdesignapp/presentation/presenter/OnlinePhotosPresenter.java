package com.orionst.mymaterialdesignapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.orionst.mymaterialdesignapp.domain.ResourceManager;
import com.orionst.mymaterialdesignapp.domain.model.entity.Image;
import com.orionst.mymaterialdesignapp.presentation.view.ImageCellView;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;
import com.orionst.mymaterialdesignapp.repositories.OnlineRepository;
import com.orionst.mymaterialdesignapp.utils.Const;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

@InjectViewState
public class OnlinePhotosPresenter extends MvpPresenter<PhotoView> implements IPresenter {

    @Inject OnlineRepository repository;
    @Inject
    @Named("Online")
    ResourceManager resourceManager;
    private Scheduler scheduler;

    private List<Image> imageList = new ArrayList<>();
    private List<Image> imageListNew = new ArrayList<>();

    private ImageListPresenter imageListPresenter = new ImageListPresenter();

    class ImageListPresenter implements IImageListPresenter {
        @Override
        public void bindRepoListRow(int pos, ImageCellView rowView) {
            rowView.setFavorite(imageList.get(pos).isFavorite());
            rowView.setImage(imageList.get(pos).getPhotoUri());
        }

        @Override
        public int getImagesCount() {
            return imageList == null ? 0 : imageList.size();
        }

        @Override
        public List<Image> getImages() {
            return imageList;
        }

        @Override
        public List<Image> getNewImages() {
            return imageListNew;
        }

        @Override
        public void applyImageList() {
            imageList.clear();
            imageList.addAll(imageListNew);
        }

        @Override
        public void onImageClick(int position) {
            String photoUriString = imageList.get(position).getPhotoUri().toString();
            getViewState().onPhotoView(photoUriString);
        }

        @Override
        public void onClickFavorite(int pos) {
            getViewState().showNotification(resourceManager.getStringNotificationOnErrorChangeFavorite());
        }

        @Override
        public void deleteImage(int pos) {
            imageListNew.clear();
            imageListNew.addAll(imageList);
            imageListNew.remove(pos);
            getViewState().onNewImageList();
        }
    }

    public OnlinePhotosPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getPhotoList();
    }

    @Override
    public void getPhotoList() {
        repository.getQueryResult(Const.API_KEY)
                .observeOn(scheduler)
                .subscribe(images -> {
                            this.imageListNew = images;
                            getViewState().onNewImageList();
                        },
                        throwable -> getViewState().showNotification(resourceManager.getStringNotificationOnErrorGetPhotoList()));
    }

    @Override
    public void onMessageEvent() {

    }

    public IImageListPresenter getImageListPresenter() {
        return imageListPresenter;
    }

}
