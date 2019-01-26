package com.jacob.unsplash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Constants;
import com.jacob.unsplash.utils.Utils;
import com.jacob.unsplash.view.pager.PagerFragment;
import com.jacob.unsplash.view.pager.PagerPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PagerActivity extends AppCompatActivity {
    boolean isReturning = false;
    private int startPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_pager);
        ActivityCompat.postponeEnterTransition(this);
        ActivityCompat.setEnterSharedElementCallback(this, new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                if (isReturning) {
                    PagerFragment pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.pager_fragment_container);
                    pagerFragment.onMapSharedElements(names, sharedElements);
                }
            }
        });
        setToolBar();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<Photo> data = bundle.getParcelableArrayList(Constants.ARG_PHOTO_LIST);
            int currentPage = bundle.getInt(Constants.ARG_POSITION);
            startPosition = currentPage;
            PagerFragment pagerFragment = PagerFragment.newInstance(startPosition);
            PagerPresenter presenter = new PagerPresenter(pagerFragment, data, currentPage);
            pagerFragment.setPresenter(presenter);
            Utils.addFragment(this, pagerFragment, R.id.pager_fragment_container);
        }
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.picture_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                supportFinishAfterTransition();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finishAfterTransition() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.pager_fragment_container);

        if (fragment instanceof CurrentItemProvider) {
            isReturning = true;
            Intent intent = new Intent();
            CurrentItemProvider provider = (CurrentItemProvider) fragment;
            intent.putExtra(MainActivity.EXTRA_STARTING_ALBUM_POSITION, startPosition);
            intent.putExtra(MainActivity.EXTRA_CURRENT_ALBUM_POSITION, provider.getCurrentItem());
            setResult(Activity.RESULT_OK, intent);
            super.finishAfterTransition();
        }
    }

    public interface CurrentItemProvider {
        int getCurrentItem();
    }
}
