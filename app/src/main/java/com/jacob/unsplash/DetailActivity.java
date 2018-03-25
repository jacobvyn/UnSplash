package com.jacob.unsplash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.view.ItemViewFragment;

import butterknife.ButterKnife;

/**
 * Created by vynnykiakiv on 3/25/18.
 */

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_item);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        final Bundle bundle = getIntent().getExtras();
        Photo photo = (Photo) bundle.getParcelable(ItemViewFragment.ARG_PHOTO);

        ItemViewFragment fragment = ItemViewFragment.newInstance(photo);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.item_view_container, fragment)
                .commit();
    }
}
