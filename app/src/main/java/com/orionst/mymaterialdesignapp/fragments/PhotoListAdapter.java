package com.orionst.mymaterialdesignapp.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.models.Photo;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>{

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView photoView;
        private final CheckBox favorite;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            this.photoView = itemView.findViewById(R.id.picture);
            this.favorite = itemView.findViewById(R.id.favorite);
        }
    }

    private final LayoutInflater mInflater;
    private final List<Photo> photos;

    public PhotoListAdapter(Context context, List<Photo> photos) {
        mInflater = LayoutInflater.from(context);
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_photo_recyclerview, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo item = photos.get(position);
        holder.photoView.setImageResource(item.getPictureId());
        holder.favorite.setSelected(item.isFavorite());
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


}
