package com.orionst.mymaterialdesignapp.presentation.view;

import android.net.Uri;

public interface ImageCellView {
    void setFavorite(boolean favorite);
    void setImage(Uri imageUri);
}
