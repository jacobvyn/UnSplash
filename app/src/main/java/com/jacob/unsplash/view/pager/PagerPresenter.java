package com.jacob.unsplash.view.pager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jacob.unsplash.R;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Constants;
import com.jacob.unsplash.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PagerPresenter implements PagerContract.Presenter, Utils.Callback {
    private static final String TYPE_TEXT_PLAIN = "text/plain";
    private final List<Photo> mPhotoList = new ArrayList<>();
    private AppCompatActivity mActivity;
    private int mCurrentPos;
    private final PagerContract.View mView;

    public PagerPresenter(AppCompatActivity activity, PagerContract.View view) {
        mActivity = activity;
        mView = view;
        mView.setPresenter(this);
        setData();
    }

    private void setData() {
        Bundle bundle = mActivity.getIntent().getExtras();
        if (bundle != null) {
            ArrayList<Photo> data = bundle.getParcelableArrayList(Constants.ARG_PHOTO_LIST);
            mPhotoList.addAll(data);
            mCurrentPos = bundle.getInt(Constants.ARG_POSITION);
        }
    }

    @Override
    public List<Photo> getData() {
        return mPhotoList;
    }

    @Override
    public int getCurrentPos() {
        return mCurrentPos;
    }

    @Override
    public void setCurrentPos(int position) {
        mCurrentPos = position;
    }

    @Override
    public void onShareClicked() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(TYPE_TEXT_PLAIN);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getCurrentUrl());
        if (mActivity != null) {
            mActivity.startActivity(Intent.createChooser(shareIntent, mActivity.getString(R.string.share_picture)));
        }
    }

    @Override
    public void onDownloadClicked() {
        Utils.savePhoto(mPhotoList.get(getCurrentPos()), this);
    }

    private String getCurrentUrl() {
        return mPhotoList.get(getCurrentPos()).getRegular();
    }

    @Override
    public void onSaveSuccess(File file) {
        Utils.notifyMediaScanner(file, mActivity);
        if (mView != null) {
            mView.showMessage(R.string.success_save);
        }
    }

    @Override
    public void onSaveFail() {
        if (mView != null) {
            mView.showMessage(R.string.error_fail_save);
        }
    }
}
