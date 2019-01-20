package com.jacob.unsplash.view.gallery;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jacob.unsplash.R;
import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Constants;
import com.jacob.unsplash.PagerActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GalleryPresenter implements GalleryContract.Presenter {
    private final GalleryContract.View mView;
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private AppCompatActivity mActivity;
    private UnSplashRepository mRepository;
    private final ArrayList<Photo> mPhotoList = new ArrayList<>();


    public GalleryPresenter(AppCompatActivity activity, UnSplashRepository repository, GalleryContract.View view) {
        mActivity = activity;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void onPhotoClicked(int position, View view) {
        Intent intent = new Intent(mActivity, PagerActivity.class);
        intent.putParcelableArrayListExtra(Constants.ARG_PHOTO_LIST, mPhotoList);
        intent.putExtra(Constants.ARG_POSITION, position);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view, view.getTag().toString());
        ActivityCompat.startActivity(mActivity, intent, optionsCompat.toBundle());
    }

    @Override
    public void onSearch(String query) {
        mView.showProgressBar(true);
        mDisposables.add(mRepository.search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::precessResult, error -> {
                    mView.onSearchFailed(error.getMessage());
                    mView.showProgressBar(false);
                }));
    }

    public void precessResult(List<Photo> photos) {
        if (photos.size() > 0) {
            mPhotoList.clear();
            mPhotoList.addAll(photos);
            mView.onSearchSucceeded(photos);
        } else {
            mView.onSearchFailed(mActivity.getString(R.string.error_nothing_found));
        }
        mView.showProgressBar(false);
    }

    @Override
    public void onDestroy() {
        mDisposables.dispose();
    }
}
