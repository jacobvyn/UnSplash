package com.jacob.unsplash.view.gallery;

import com.jacob.unsplash.R;
import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.model.Photo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GalleryPresenter implements GalleryContract.Presenter {
    private GalleryContract.View mView;
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private UnSplashRepository mRepository;
    private final ArrayList<Photo> mPhotoList = new ArrayList<>();

    public GalleryPresenter(UnSplashRepository repository, GalleryContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
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
            mView.onSearchFailed(R.string.error_nothing_found);
        }
        mView.showProgressBar(false);
    }

    @Override
    public void onDestroy() {
        mDisposables.dispose();
        mView = null;
    }

    @Override
    public ArrayList<Photo> getData() {
        return mPhotoList;
    }
}
