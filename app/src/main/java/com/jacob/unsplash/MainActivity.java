package com.jacob.unsplash;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jacob.unsplash.R;
import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.db.MockDataBase;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Utils;
import com.jacob.unsplash.view.GalleryFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener,
        UnSplashRepository.Listener {

    private static final String ARG_LIST = "ARG_LIST";
    private UnSplashRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        GalleryFragment fragment = (GalleryFragment) getSupportFragmentManager().findFragmentByTag(GalleryFragment.TAG);
        if (fragment == null) {
            fragment = GalleryFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.gallery_fragment_container, fragment, GalleryFragment.TAG)
                    .commit();
        }

        repository = UnSplashRepository.getInstance();
        repository.setListener(this);

        if (savedInstanceState != null) {
            ArrayList<Photo> photos = (ArrayList<Photo>) savedInstanceState.get(ARG_LIST);
            MockDataBase.getInstance().setData(photos);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ARG_LIST, MockDataBase.getInstance().getData());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.getItem(0);
        if (item.getActionView() instanceof SearchView) {
            ((SearchView) item.getActionView()).setOnQueryTextListener(this);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_search || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String newExpression) {
        repository.search(newExpression);
        Utils.hideSoftKeyboard(this);
        onSearchStart();
        return true;
    }

    private void onSearchStart() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(GalleryFragment.TAG);
        if (fragment instanceof GalleryFragment) {
            ((GalleryFragment) fragment).onSearchStart();
        }
    }

    @Override
    public boolean onQueryTextChange(String newExpression) {
        return false;
    }

    @Override
    public void onSuccess(List<Photo> photos) {
        MockDataBase.getInstance().setData(photos);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(GalleryFragment.TAG);
        if (fragment instanceof GalleryFragment) {
            ((GalleryFragment) fragment).setList(photos);
        }
    }

    @Override
    public void onFail(String message) {
        Snackbar.make(findViewById(R.id.activity_main_root), message, Snackbar.LENGTH_LONG).show();
    }
}
