package com.orionst.mymaterialdesignapp.fragments.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.presentation.presenter.IImageListPresenter;
import com.orionst.mymaterialdesignapp.presentation.view.ImageCellView;
import com.orionst.mymaterialdesignapp.utils.CropSquareTransformation;
import com.orionst.mymaterialdesignapp.utils.MyDiffImagesCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    IImageListPresenter presenter;
    private LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder implements ImageCellView {

        @BindView(R.id.picture) ImageView photoView;
        @BindView(R.id.favorite) ImageView favoriteView;
        @BindView(R.id.txtOptionDigit) TextView txtOptionMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setFavorite(boolean favorite) {
            if (favorite) {
                favoriteView.setImageResource(R.drawable.ic_favorite_true);
            } else {
                favoriteView.setImageResource(R.drawable.ic_favorite_false);
            }
        }

        @Override
        public void setImage(Uri imageUri) {
            Glide.with(mInflater.getContext())
                    .load(imageUri)
                    .apply(bitmapTransform(new CropSquareTransformation()))
                    .into(photoView);
        }

        @OnClick(R.id.picture)
        void onClickImage() {
            presenter.onImageClick(getAdapterPosition());
        }

        @OnClick(R.id.favorite)
        void onClickFavorite() {
            presenter.onClickFavorite(getAdapterPosition());
        }

        @OnClick(R.id.txtOptionDigit)
        void setTxtOptionMenu() {
            android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(this.txtOptionMenu.getContext(), this.txtOptionMenu);
            popupMenu.inflate(R.menu.menu_action_mode);
            popupMenu.setOnMenuItemClickListener(menuItem -> {

                switch (menuItem.getItemId()) {
                    case R.id.action_item_delete:
                        presenter.deleteImage(getAdapterPosition());
                        break;
                    default:
                        break;
                }
                return false;
            });
            popupMenu.show();
        }

    }

    public ImageListAdapter(IImageListPresenter presenter, Context context) {
        this.presenter = presenter;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ViewHolder(mInflater.from(parent.getContext()).inflate(R.layout.item_photo_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        presenter.bindRepoListRow(i, viewHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.getImagesCount();
    }

    public void updateList() {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffImagesCallback(presenter.getImages(), presenter.getNewImages()));
        diffResult.dispatchUpdatesTo(this);
        presenter.applyImageList();
    }

}
