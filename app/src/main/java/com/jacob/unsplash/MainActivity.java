package com.jacob.unsplash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.view.MainViewFragment;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private UnSplashRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainViewFragment fragment = MainViewFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.root_frame_layout, fragment, MainViewFragment.TAG)
                .commit();

        repository = UnSplashRepository.getInstance();
        repository.setListener(fragment);
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
        repository.searchFor(newExpression);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newExpression) {
        return true;
    }
}
