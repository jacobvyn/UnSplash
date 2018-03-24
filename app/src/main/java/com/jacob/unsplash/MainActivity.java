package com.jacob.unsplash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.view.MainViewFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, UnSplashRepository.Listener {

    private UnSplashRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.root_frame_layout, MainViewFragment.newInstance(), MainViewFragment.TAG)
                .commit();

        repository = UnSplashRepository.getInstance();
        repository.setListener(this);
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
        repository.searchInBackfround(newExpression);
        return true;
    }

    @Override
    public void onSuccess(List<Photo> photos) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainViewFragment.TAG);
        if (fragment instanceof MainViewFragment) {
            ((MainViewFragment) fragment).setList(photos);
        }
    }

    @Override
    public void onFail(String message) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainViewFragment.TAG);
        if (fragment instanceof MainViewFragment) {
            ((MainViewFragment) fragment).onFail(message);
        }
    }

    @Override
    public boolean onQueryTextChange(String newExpression) {
        return false;
    }
}
