package com.jacob.unsplash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.utils.Utils;
import com.jacob.unsplash.view.gallery.GalleryFragment;
import com.jacob.unsplash.view.gallery.GalleryPresenter;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_STARTING_ALBUM_POSITION = "extra_starting_item_position";
    public static final String EXTRA_CURRENT_ALBUM_POSITION = "extra_current_item_position";
    private GalleryPresenter mPresenter;
    private Bundle reenterState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.setExitSharedElementCallback(this, new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (reenterState != null) {
                    int start = reenterState.getInt(MainActivity.EXTRA_STARTING_ALBUM_POSITION);
                    int current = reenterState.getInt(MainActivity.EXTRA_CURRENT_ALBUM_POSITION);
                    if (start != current) {
                        GalleryFragment galleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.gallery_fragment_container);
                        galleryFragment.onMapSharedElements(names, sharedElements, current);
                    }
                    reenterState = null;
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        GalleryFragment galleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.gallery_fragment_container);
        if (galleryFragment == null) {
            galleryFragment = GalleryFragment.newInstance();
        }

        mPresenter = new GalleryPresenter(UnSplashRepository.getInstance(), galleryFragment);
        Utils.addFragment(this, galleryFragment, R.id.gallery_fragment_container);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        reenterState = new Bundle(data.getExtras());
        ActivityCompat.postponeEnterTransition(this);
        GalleryFragment galleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.gallery_fragment_container);
        galleryFragment.onActivityReenter();
    }
}
