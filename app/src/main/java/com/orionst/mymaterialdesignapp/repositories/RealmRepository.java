package com.orionst.mymaterialdesignapp.repositories;

import android.net.Uri;

import com.orionst.mymaterialdesignapp.domain.model.entity.Image;
import com.orionst.mymaterialdesignapp.domain.model.entity.realm.RealmImage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmRepository {

    public Completable saveImageToDB(String uriString, boolean favorite) {
        return Completable.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            RealmImage realmImage = realm.where(RealmImage.class).equalTo("uriString", uriString).findFirst();
            if (realmImage == null) {
                realm.executeTransaction(innerRealm -> {
                    RealmImage newRealmImage = innerRealm.createObject(RealmImage.class, uriString);
                    newRealmImage.setFavorite(favorite);
                    emitter.onComplete();
                });
            } else {
                realm.executeTransaction(innerRealm -> {
                    realmImage.setFavorite(favorite);
                });
            }
            realm.close();
        });
    }

    public Single<Image> getImageFromDB(String uriString) {

        return Single.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            RealmImage realmImage = realm.where(RealmImage.class).equalTo("uriString", uriString).findFirst();
            if (realmImage != null) {
                emitter.onSuccess(new Image(Uri.parse(realmImage.getUriString()), realmImage.isFavorite(), false));
            } else {
                emitter.onError(new RuntimeException());
            }
            realm.close();
        });
    }

    public Completable updateImageInDB(Image image) {
        return Completable.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            RealmImage realmImage = realm.where(RealmImage.class).equalTo("uriString", image.getPhotoUri().toString()).findFirst();
            if (realmImage == null) {
                emitter.onError(new RuntimeException());
            } else {
                realm.executeTransaction(innerRealm -> realmImage.setFavorite(!image.isFavorite()));
                emitter.onComplete();
            }
            realm.close();
        });
    }

    public Completable deleteImageFromDB(Image image) {

        return Completable.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            final boolean[] deleteResult = {false};
            realm.executeTransaction(realm1 -> {
                RealmResults<RealmImage> result = realm1.where(RealmImage.class).equalTo("uriString", image.getPhotoUri().toString()).findAll();
                deleteResult[0] = result.deleteAllFromRealm();
                emitter.onComplete();
            });
            realm.close();
        });
    }

    public Observable<List<Image>> getAllImages() {

        return Observable.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            List<RealmImage> realmImages = realm.where(RealmImage.class).findAll();
            List<Image> images = new ArrayList<>();
            if (!realmImages.isEmpty()) {
                for (RealmImage realmImage : realmImages) {
                    images.add(new Image(Uri.parse(realmImage.getUriString()), realmImage.isFavorite(), false));
                }
            }
            emitter.onNext(images);
            emitter.onComplete();
            realm.close();
        });
    }

    public Observable<List<Image>> getFavoriteImages() {

        return Observable.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            List<Image> images = new ArrayList<>();
            List<RealmImage> realmImages = realm.where(RealmImage.class).equalTo("favorite", true).findAll();
            if (!realmImages.isEmpty()) {
                for (RealmImage realmImage : realmImages) {
                    images.add(new Image(Uri.parse(realmImage.getUriString()), realmImage.isFavorite(), false));
                }
            }
            emitter.onNext(images);
            emitter.onComplete();
            realm.close();
        });
    }

}
