package com.jacob.unsplash.view.pager;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.jacob.unsplash.R;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by vynnykiakiv on 3/26/18.
 */

public class PhotoFragment extends Fragment implements Target {

    @BindView(R.id.picture_image_view)
    protected JacobsImageView mPictureImageView;
    @BindView(R.id.progress_bar)
    protected AVLoadingIndicatorView mProgressBar;
    @BindView(R.id.detail_fragment_root)
    protected View mRootView;
    private Photo mPhoto;
    private Unbinder mUnBinder;
    private int current = 0;

    public static PhotoFragment newInstance(Photo photo, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARG_POSITION, position);
        bundle.putParcelable(Constants.ARG_PHOTO, photo);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args != null) {
            mPhoto = (Photo) args.getParcelable(Constants.ARG_PHOTO);
            current = args.getInt(Constants.ARG_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_view_layout, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        mPictureImageView.setTag(current);

        if (getParentFragment() instanceof OnDragListener) {
            OnDragListener listener = (OnDragListener) getParentFragment();
            mPictureImageView.setListener(listener);
        }
        ViewCompat.setTransitionName(mPictureImageView, Constants.SHARED_VIEW_PREFIX + current);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        showProgressBar(true);
        Picasso.get().load(mPhoto.getSmall())
                .error(android.R.drawable.stat_notify_error)
                .into(PhotoFragment.this);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

        mPhoto.setBitmap(bitmap);
        showProgressBar(false);
        mPictureImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mPictureImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (isStartPage()) {
                    ActivityCompat.startPostponedEnterTransition(getActivity());
                }
                return true;
            }
        });

        if (mPictureImageView != null) {
            mPictureImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
        if (mPictureImageView != null) {
            mPictureImageView.setImageDrawable(errorDrawable);
        }
        showProgressBar(false);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }

    private void showProgressBar(boolean isEnabled) {
        if (mProgressBar != null) {
            if (isEnabled) {
                mProgressBar.smoothToShow();
            } else {
                mProgressBar.smoothToHide();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    public int getCurrent() {
        return current;
    }

    public boolean isStartPage() {
        if (getParentFragment() instanceof PagerFragment) {
            PagerFragment fragment = (PagerFragment) getParentFragment();
            return current == fragment.getStartPosition();

        } else return false;
    }
}
