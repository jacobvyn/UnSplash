package com.jacob.unsplash.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jacob.unsplash.R;
import com.jacob.unsplash.api.UnSplashRepository;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Constants;
import com.jacob.unsplash.utils.Utils;
import com.jacob.unsplash.view.adapter.PhotoRecycleAdapter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GalleryFragment extends Fragment
        implements PhotoRecycleAdapter.OnItemClickListener, SearchView.OnQueryTextListener {

    public static final String TAG = GalleryFragment.class.getName();
    private static final int COLUMNS = 2;
    private PhotoRecycleAdapter mAdapter;
    private View mProgressBar;

    private CompositeDisposable mDisposables = new CompositeDisposable();

    public static GalleryFragment newInstance() {
        final GalleryFragment fragment = new GalleryFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mProgressBar = view.findViewById(R.id.progress_bar_main);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), COLUMNS);
        recyclerView.setLayoutManager(manager);
        mAdapter = new PhotoRecycleAdapter(getActivity());
        mAdapter.setOnCLickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.getItem(0);
        if (item.getActionView() instanceof SearchView) {
            ((SearchView) item.getActionView()).setOnQueryTextListener(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposables.clear();
    }

    @Override
    public void onItemClicked(Photo photo, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putParcelableArrayListExtra(Constants.ARG_PHOTO_LIST, mAdapter.getData());
        intent.putExtra(Constants.ARG_POSITION, position);
        getActivity().startActivity(intent);
    }

    public void setList(List<Photo> photos) {
        if (mAdapter != null && photos.size() > 0) {
            mAdapter.setList(photos);
        } else {
            Snackbar.make(getView(), getString(R.string.error_nothing_found), Snackbar.LENGTH_LONG).show();
        }
        onSearchFinish();
    }

    public void onSearchStart() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void onSearchFinish() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void onFail(String message) {
        onSearchFinish();
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mDisposables.add(
                UnSplashRepository.getInstance().search(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<Photo>>() {

                            @Override
                            public void onNext(List<Photo> photos) {
                                setList(photos);
                            }

                            @Override
                            public void onError(Throwable e) {
                                onFail(e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                            }
                        }));

        Utils.hideSoftKeyboard(getActivity());
        onSearchStart();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
