package com.jacob.unsplash.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.jacob.unsplash.R;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by vynnykiakiv on 3/26/18.
 */

public class ItemViewFragment extends Fragment implements Callback, Utils.Callback {
    private static final String TYPE_TEXT_PLAIN = "text/plain";
    public static final String ARG_PHOTO = "ARG_PHOTO";

    @BindView(R.id.picture_image_view)
    protected ImageView mPictureImageView;
    @BindView(R.id.progress_bar)
    protected AVLoadingIndicatorView mProgressBar;
    @BindView(R.id.detail_fragment_root)
    protected View mRootView;
    private Photo mPhoto;
    private Unbinder mUnBinder;
    private Animation mAlphaAnim;

    public static ItemViewFragment newInstance(Photo photo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PHOTO, photo);
        ItemViewFragment fragment = new ItemViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mAlphaAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha);
        mPhoto = getPhoto(getArguments());
        if (mProgressBar != null) {
            mProgressBar.smoothToShow();
        }
        Picasso.get().load(mPhoto.getRegular())
                .error(android.R.drawable.stat_notify_error)
                .into(mPictureImageView, this);
    }

    private Photo getPhoto(Bundle bundle) {
        return (Photo) bundle.getParcelable(ARG_PHOTO);
    }


    @Override
    public void onSuccess() {
        if (mProgressBar != null) {
            mProgressBar.smoothToHide();
        }
    }

    @Override
    public void onError(Exception e) {
        mProgressBar.smoothToHide();
    }

    @OnClick(R.id.item_share_image_view)
    protected void onShareCLick(View view) {
        animate(view);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(TYPE_TEXT_PLAIN);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mPhoto.getRegular());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_picture)));
    }

    private void animate(View view) {
        if (view != null) {
            view.startAnimation(mAlphaAnim);
        }
    }

    @OnClick(R.id.item_download_image_view)
    protected void onDownloadClick(View view) {
        animate(view);
        Utils.saveImage(mPictureImageView, this);
    }

    @Override
    public void onSaveSuccess(File file) {
        notifyMediaScanner(file);
        Snackbar.make(mRootView, "Photo successfully saved.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSaveFail() {
        Snackbar.make(mRootView, "Failed to successfully a photo.", Snackbar.LENGTH_LONG).show();
    }

    private void notifyMediaScanner(File outputFile) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(outputFile));
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }
}
