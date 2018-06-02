package com.jacob.unsplash.view.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacob.unsplash.R;
import com.jacob.unsplash.utils.DepthPageTransformer;
import com.jacob.unsplash.utils.PermissionHelper;

public class PagerFragment extends Fragment
        implements ViewPager.OnPageChangeListener, PagerContract.View, View.OnClickListener {

    private PagerContract.Presenter mPresenter;
    private View mRootView;
    private AppCompatActivity mActivity;

    public static PagerFragment newInstance() {
        return new PagerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            mActivity = (AppCompatActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRootView = view;
        view.findViewById(R.id.item_download_image_view).setOnClickListener(this);
        view.findViewById(R.id.item_share_image_view).setOnClickListener(this);

        ViewPager pager = (ViewPager) view.findViewById(R.id.view_pager);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setAdapter(new PhotoPagerAdapter(getChildFragmentManager(), mPresenter.getData()));
        pager.setOnPageChangeListener(this);
        pager.setCurrentItem(mPresenter.getCurrentPos());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_share_image_view: {
                onShareClicked();
                break;
            }
            case R.id.item_download_image_view: {
                askPermissionAndDownload();
                break;
            }
        }
    }

    private void askPermissionAndDownload() {
        if (PermissionHelper.hasPermission(mActivity)) {
            onDownloadClicked();
        } else {
            PermissionHelper.requestPermission(mActivity);
        }
    }

    private void onShareClicked() {
        if (mPresenter != null) {
            mPresenter.onShareClicked();
        }
    }

    private void onDownloadClicked() {
        if (mPresenter != null) {
            mPresenter.onDownloadClicked();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionHelper.hasPermission(mActivity)) {
            onDownloadClicked();
        } else {
            Snackbar.make(mRootView, R.string.error_need_permission, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPresenter(PagerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(int messageId) {
        Snackbar.make(mRootView, messageId, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mPresenter.setCurrentPos(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onDestroyView() {
        mActivity = null;
        super.onDestroyView();
    }
}
