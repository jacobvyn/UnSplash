package com.jacob.unsplash.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.jacob.unsplash.DepthPageTransformer;
import com.jacob.unsplash.R;
import com.jacob.unsplash.ZoomOutPageTransformer;
import com.jacob.unsplash.db.MockDataBase;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Constants;
import com.jacob.unsplash.utils.PermissionHelper;
import com.jacob.unsplash.utils.Utils;
import com.jacob.unsplash.view.adapter.PhotoPagerAdapter;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, Utils.Callback {
    private static final String TYPE_TEXT_PLAIN = "text/plain";
    private ArrayList<Photo> mData;
    private int mCurrentPos;
    @BindView(R.id.activity_detail_root)
    protected View mRootView;
    private Animation mAlphaAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mAlphaAnim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        mData = MockDataBase.getInstance().getData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.picture_view);

        Bundle bundle = getIntent().getExtras();
        mCurrentPos = bundle.getInt(Constants.ARG_POSITION);
        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setAdapter(new PhotoPagerAdapter(getSupportFragmentManager(), mData));
        pager.setOnPageChangeListener(this);
        pager.setCurrentItem(mCurrentPos);
    }

    @OnClick(R.id.item_share_image_view)
    protected void onShareCLick(View view) {
        animate(view);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType(TYPE_TEXT_PLAIN);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mData.get(mCurrentPos).getRegular());
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
        if (PermissionHelper.hasPermission(this)) {
            Utils.savePhoto(mData.get(mCurrentPos), this);
        } else {
            PermissionHelper.requestPermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PermissionHelper.hasPermission(this)) {
            Utils.savePhoto(mData.get(mCurrentPos), this);
        } else {
            Snackbar.make(mRootView, R.string.error_need_permission, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveSuccess(File file) {
        notifyMediaScanner(file);
        Snackbar.make(mRootView, R.string.success_save, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSaveFail() {
        Snackbar.make(mRootView, R.string.error_fail_save, Snackbar.LENGTH_LONG).show();
    }

    private void notifyMediaScanner(File outputFile) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(outputFile));
        sendBroadcast(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPos = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
