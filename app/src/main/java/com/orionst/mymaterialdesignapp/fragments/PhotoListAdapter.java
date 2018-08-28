package com.orionst.mymaterialdesignapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>{

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
        Uri uri = item.getPhotoUri();
        holder.bind(uri);
        holder.favoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhotoListener.onEntityChange(item);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mPhotos == null) {
            return 0;
        }
        return mPhotos.size();
    }

    void setPhotos(List<Photo> photos){
        this.mPhotos = photos;
        notifyDataSetChanged();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView photoView;
        private final ImageView favoriteView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            this.photoView = itemView.findViewById(R.id.picture);
            this.favoriteView = itemView.findViewById(R.id.favorite);
        }

        public void bind(Uri uri) {
            Picasso.with(itemView.getContext())
                    .load(uri)
                    .into(photoView);
        }
    }

    interface EntitiesListener {
        void onEntityChange(Photo item);
    }


}
