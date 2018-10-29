package com.orionst.mymaterialdesignapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.orionst.mymaterialdesignapp.domain.ResourceManager;
import com.orionst.mymaterialdesignapp.domain.model.entity.Image;
import com.orionst.mymaterialdesignapp.presentation.view.ImageCellView;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;
import com.orionst.mymaterialdesignapp.repositories.OnlineRepository;
import com.orionst.mymaterialdesignapp.repositories.RealmRepository;
import com.orionst.mymaterialdesignapp.utils.Const;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class CommonListPresenter extends MvpPresenter<PhotoView> implements IPresenter {

    private Scheduler observeScheduler;
    @Inject
    @Named("Common")
    ResourceManager resourceManager;
    @Inject RealmRepository dbRepo;
    @Inject OnlineRepository onlineRepo;

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
        public void deleteImage(int position) {
            if (imageList.get(position).isRemote()) {
                imageListNew.clear();
                imageListNew.addAll(imageList);
                imageListNew.remove(position);
                getViewState().onNewImageList();
            } else {
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
        }

        @Override
        public void onClickFavorite(int pos) {
            Image item = imageList.get(pos);
            if (item.isRemote()) {
                getViewState().showNotification("Photo from online repository can't be marked");
            } else {
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
    }

    public CommonListPresenter(Scheduler observeScheduler) {
        this.observeScheduler = observeScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getPhotoList();
    }

    public ImageListPresenter getImageListPresenter() {
        return imageListPresenter;
    }

    @Override
    public void getPhotoList() {
        this.imageListNew.clear();
        dbRepo.getAllImages()
                .mergeWith(onlineRepo.getQueryResult(Const.API_KEY))
                .observeOn(observeScheduler)
                .subscribe(images -> {
                    this.imageListNew.addAll(images);
                    getViewState().onNewImageList();
        }, throwable -> {
            getViewState().showNotification(resourceManager.getStringNotificationOnErrorGetPhotoList());
        });

    }

    @Override
    public void onMessageEvent() {
        getPhotoList();
    }

    public void addImage(String uriString) {
        dbRepo.saveImageToDB(uriString, false)
                .subscribeOn(Schedulers.io())
                .observeOn(observeScheduler)
                .subscribe(() -> {
//                        dbRepo.getImageFromDB(uriString)
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(observeScheduler)
//                                .subscribe(image -> {
//                                    imageListNew.add(0,image);
//                                    getViewState().onNewImageList();
//                                });
                    getPhotoList();
                        },
                        throwable -> {
                            getViewState().showNotification(resourceManager.getStringNotificationOnNewImageError());
                        }
                );
    }
}
