package com.jacob.unsplash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jacob.unsplash.api.UnSplashRepository;
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

        GalleryFragment galleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentByTag(GalleryFragment.TAG);
        if (galleryFragment == null) {
            galleryFragment = GalleryFragment.newInstance();
        }

        mPresenter = new GalleryPresenter(this, UnSplashRepository.getInstance(), galleryFragment);
        addFragment(galleryFragment, GalleryFragment.TAG);
    }

    private void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.gallery_fragment_container, fragment, tag)
                .commit();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
