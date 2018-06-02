package com.jacob.unsplash.view.gallery;

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
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Utils;

import java.util.List;

public class GalleryFragment extends Fragment implements SearchView.OnQueryTextListener, GalleryContract.View {

    public static final String TAG = GalleryFragment.class.getName();
    private static final int COLUMNS = 2;
    private GalleryRVAdapter mAdapter;
    private View mProgressBar;
    private GalleryContract.Presenter mPresenter;
    private View mRootView;


    public static GalleryFragment newInstance() {
        final GalleryFragment fragment = new GalleryFragment();
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallary_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRootView = view;
        mProgressBar = view.findViewById(R.id.progress_bar_main);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), COLUMNS);
        recyclerView.setLayoutManager(manager);
        mAdapter = new GalleryRVAdapter(mPresenter);
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
    public void onSearchSucceeded(List<Photo> photoList) {
        if (mAdapter != null) {
            mAdapter.setList(photoList);
        }
    }

    public void onSearchFailed(String message) {
        Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (mPresenter != null) {
            mPresenter.onSearch(query);
        }
        Utils.hideSoftKeyboard(getActivity());
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void setPresenter(GalleryContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean isEnabled) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        }
    }
}
