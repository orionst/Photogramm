package com.orionst.mymaterialdesignapp.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.utils.CropSquareTransformation;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

    private final LayoutInflater mInflater;
    private List<Photo> mPhotos;

    private EntitiesListener mPhotoListener;

    public PhotoListAdapter(Context context, EntitiesListener entitiesListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mPhotoListener = entitiesListener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_photo_recyclerview, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo item = mPhotos.get(position);
        if (item.isFavorite()) {
            holder.favoriteView.setImageResource(R.drawable.ic_favorite_true);
        } else {
            holder.favoriteView.setImageResource(R.drawable.ic_favorite_false);
        }
        Glide.with(mInflater.getContext())
                .load(item.getPhotoUri())
                .apply(bitmapTransform(new CropSquareTransformation()))
                .into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        if (mPhotos == null) {
            return 0;
        }
        return mPhotos.size();
    }

    void setPhotos(List<Photo> photos) {
        this.mPhotos = photos;
        notifyDataSetChanged();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView photoView;
        private final ImageView favoriteView;
        private final TextView txtOptionDigit;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            this.photoView = itemView.findViewById(R.id.picture);
            this.favoriteView = itemView.findViewById(R.id.favorite);
            this.txtOptionDigit = itemView.findViewById(R.id.txtOptionDigit);

            this.favoriteView.setOnClickListener(this::onClick);
            this.txtOptionDigit.setOnClickListener(this::onClick);
        }

        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                switch (view.getId()) {

                    case R.id.favorite:
                        mPhotoListener.onEntityChange(position);
                        break;

                    case R.id.txtOptionDigit:
                        //Display option menu
                        android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(this.txtOptionDigit.getContext(), this.txtOptionDigit);
                        popupMenu.inflate(R.menu.menu_action_mode);
                        popupMenu.setOnMenuItemClickListener(menuItem -> {

                            switch (menuItem.getItemId()) {
                                case R.id.action_item_delete:
                                    if (mPhotoListener.onEntityDelete(position)) {
                                        mPhotos.remove(position);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        });
                        popupMenu.show();
                        break;
                }
            }
        }
    }


    interface EntitiesListener {
        void onEntityChange(int item);
        boolean onEntityDelete(int item);
    }


}
