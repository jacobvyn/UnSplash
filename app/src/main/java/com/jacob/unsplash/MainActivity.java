package com.jacob.unsplash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.utils.Utils;
import com.jacob.unsplash.view.gallery.GalleryFragment;
import com.jacob.unsplash.view.gallery.GalleryPresenter;

public class MainActivity extends AppCompatActivity {

    private GalleryPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        GalleryFragment galleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.gallery_fragment_container);
        if (galleryFragment == null) {
            galleryFragment = GalleryFragment.newInstance();
        }

        mPresenter = new GalleryPresenter(this, UnSplashRepository.getInstance(), galleryFragment);
        Utils.addFragment(this, galleryFragment, R.id.gallery_fragment_container);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
