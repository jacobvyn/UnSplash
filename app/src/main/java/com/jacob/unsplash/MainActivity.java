package com.jacob.unsplash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.utils.Utils;
import com.jacob.unsplash.view.GalleryFragment;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private UnSplashRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(GalleryFragment.TAG);
        if (fragment == null) {
            fragment = GalleryFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_frame_layout, fragment, GalleryFragment.TAG)
                    .commit();
        }

        repository = UnSplashRepository.getInstance();
        repository.setListener((UnSplashRepository.Listener) fragment);
//        repository.search("cars");
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
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newExpression) {
        return true;
    }
}
