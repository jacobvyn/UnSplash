package com.jacob.unsplash.view.pager;

import com.jacob.unsplash.R;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PagerPresenter implements PagerContract.Presenter, Utils.Callback {
    private final List<Photo> mPhotoList = new ArrayList<>();
    private int mCurrentPos;
    private PagerContract.View mView;

    public PagerPresenter(PagerContract.View view, List<Photo> data, int currentPage) {
        mView = view;
        mPhotoList.addAll(data);
        mCurrentPos = currentPage;
    }

    @Override
    public void setCurrentPos(int position) {
        mCurrentPos = position;
    }

    @Override
    public void onDownloadClicked() {
        Utils.savePhoto(mPhotoList.get(mCurrentPos), this);
    }

    @Override
    public void release() {
        mView = null;
    }

    @Override
    public String getCurrentUrl() {
        return mPhotoList.get(mCurrentPos).getRegular();
    }

    @Override
    public void start() {
        if (mView != null) {
            mView.setData(mPhotoList, mCurrentPos);
        }
    }

    @Override
    public void onSaveSuccess(File file) {
        if (mView != null) {
            mView.onSaveSuccess(file);
        }
    }

    @Override
    public void onSaveFail() {
        if (mView != null) {
            mView.showMessage(R.string.error_fail_save);
        }
    }
}
