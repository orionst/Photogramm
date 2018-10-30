package com.orionst.mymaterialdesignapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.orionst.mymaterialdesignapp.domain.ResourceManager;
import com.orionst.mymaterialdesignapp.domain.model.entity.Image;
import com.orionst.mymaterialdesignapp.presentation.view.ImageCellView;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;
import com.orionst.mymaterialdesignapp.repositories.RealmRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DBPhotosPresenter extends MvpPresenter<PhotoView> implements IPresenter {

    private Scheduler observeScheduler;
    @Inject RealmRepository dbRepo;
    @Inject
    @Named("DB")
    ResourceManager resourceManager;

    private List<Image> imageList = new ArrayList<>();
    private List<Image> imageListNew;

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
        public void deleteImage(int position) {
            dbRepo.deleteImageFromDB(imageList.get(position))
                    .subscribeOn(Schedulers.io())
                    .observeOn(observeScheduler)
                    .subscribe(
                            () -> {
                                getViewState().showNotification(resourceManager.getStringNotificationOnDeleteImage());
                                getPhotoList();
                                getViewState().sendReloadListMessage();
                            },
                            throwable ->
                                    getViewState().showNotification(resourceManager.getStringNotificationOnDeleteImageError()));
        }

        @Override
        public void onClickFavorite(int pos) {
            Image item = imageList.get(pos);
            dbRepo.updateImageInDB(item)
                    .subscribeOn(Schedulers.io())
                    .observeOn(observeScheduler)
                    .subscribe(
                            () -> {
                                getViewState().showNotification(resourceManager.getStringNotificationOnChangeFavorite(item.isFavorite()));
                                getPhotoList();
                                getViewState().sendReloadListMessage();
                            },
                            throwable ->
                                    getViewState().showNotification(resourceManager.getStringNotificationOnErrorChangeFavorite()));

        }
    }


    public DBPhotosPresenter(Scheduler scheduler) {
        this.observeScheduler = scheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getPhotoList();
    }

    public ImageListPresenter getImageListPresenter() {
        return imageListPresenter;
    }

    public void getPhotoList() {
        dbRepo.getAllImages()
                .observeOn(observeScheduler)
                .subscribe(images -> {
                    this.imageListNew = images;
                    getViewState().onNewImageList();
                }, throwable -> {
                    getViewState().showNotification(resourceManager.getStringNotificationOnErrorGetPhotoList());
                });
    }

    @Override
    public void onMessageEvent() {
        getPhotoList();
    }

}
