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
import com.jacob.unsplash.view.gallery.GalleryPresenter;

import java.util.List;
import java.util.Map;

public class PagerFragment extends Fragment
        implements ViewPager.OnPageChangeListener, PagerContract.View, View.OnClickListener {

    private PagerContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    public static int CURRENT_PAGE;
    private ViewPager pager;

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
        view.findViewById(R.id.item_download_image_view).setOnClickListener(this);
        view.findViewById(R.id.item_share_image_view).setOnClickListener(this);

        pager = (ViewPager) view.findViewById(R.id.view_pager);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setAdapter(new PhotoPagerAdapter(getChildFragmentManager(), mPresenter.getData()));
        pager.addOnPageChangeListener(this);
        CURRENT_PAGE = mPresenter.getCurrentPos();
        pager.setCurrentItem(CURRENT_PAGE);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                CURRENT_PAGE = position;
            }
        });
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
            Snackbar.make(getView(), R.string.error_need_permission, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void setPresenter(PagerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(int messageId) {
        Snackbar.make(getView(), messageId, Snackbar.LENGTH_SHORT).show();
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

    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        if (GalleryPresenter.START_PAGE != PagerFragment.CURRENT_PAGE) {

            List<Fragment> fragments = getChildFragmentManager().getFragments();
            View sharedView = null;

            for (Fragment frag : fragments) {
                if (frag instanceof PhotoFragment) {
                    PhotoFragment fragment = (PhotoFragment) frag;
                    if (fragment.getCurrent() == pager.getCurrentItem()) {
                        sharedView = fragment.getView().findViewById(R.id.picture_image_view);
                    }
                }
            }

            if (sharedView != null) {
                String transitionName = sharedView.getTag().toString();
                names.clear();
                names.add(transitionName);

                sharedElements.clear();
                sharedElements.put(transitionName, sharedView);
            }
        }
    }
}
