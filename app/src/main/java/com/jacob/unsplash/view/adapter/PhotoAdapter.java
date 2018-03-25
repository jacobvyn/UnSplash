package com.jacob.unsplash.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jacob.unsplash.R;
import com.jacob.unsplash.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vynnykiakiv on 3/24/18.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private final List<Photo> mPhotoList = new ArrayList<Photo>();

    private Context mContext;
    private OnItemClickListener mListener;

    public PhotoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = mPhotoList.get(position);
        Picasso.get()
                .load(photo.getSmall())
                .error(R.drawable.download_error_place_holder)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public void setOnCLickListener(OnItemClickListener clickListener) {
        mListener = clickListener;
    }

    public void setList(List<Photo> photoList) {
        mPhotoList.clear();
        mPhotoList.addAll(photoList);
        notifyDataSetChanged();
    }

    private Photo getItem(int position) {
        return mPhotoList.get(position);
    }

    protected class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView imageView;

        protected PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_view);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClicked(getItem(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Photo photo);
    }
}
