package com.orionst.mymaterialdesignapp.presentation.presenter;

import com.orionst.mymaterialdesignapp.domain.model.entity.Image;
import com.orionst.mymaterialdesignapp.presentation.view.ImageCellView;

import java.util.List;

public interface IImageListPresenter {
    void bindRepoListRow(int pos, ImageCellView rowView);
    int getImagesCount();
    List<Image> getImages();
    List<Image> getNewImages();
    void applyImageList();
    void onImageClick(int pos);
    void onClickFavorite(int pos);
    void deleteImage(int pos);

}
