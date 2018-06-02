package com.jacob.unsplash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jacob.unsplash.view.pager.PagerFragment;
import com.jacob.unsplash.view.pager.PagerPresenter;

public class PagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        setToolBar();

        PagerFragment pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.pager_fragment_container);

        if (pagerFragment == null) {
            pagerFragment = PagerFragment.newInstance();
        }

        new PagerPresenter(this, pagerFragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.pager_fragment_container, pagerFragment)
                .commit();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.picture_view);
    }
}
