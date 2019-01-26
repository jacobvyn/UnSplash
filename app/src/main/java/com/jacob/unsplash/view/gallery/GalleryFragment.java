package com.jacob.unsplash.view.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.jacob.unsplash.PagerActivity;
import com.jacob.unsplash.R;
import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.utils.Constants;
import com.jacob.unsplash.utils.Utils;

import java.util.List;
import java.util.Map;

public class GalleryFragment extends Fragment implements SearchView.OnQueryTextListener, GalleryContract.View, OnItemClickListener {

    private static final int COLUMNS = 2;
    private GalleryRVAdapter mAdapter;
    private View mProgressBar;
    private GalleryContract.Presenter mPresenter;
    private View mRootView;
    private RecyclerView recyclerView;


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

        recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), COLUMNS);
        recyclerView.setLayoutManager(manager);
        mAdapter = new GalleryRVAdapter(this);
        recyclerView.setAdapter(mAdapter);
        mPresenter.onSearch("ass");
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

    public void onSearchFailed(int resId) {
        onSearchFailed(getString(resId));
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

    public void onActivityReenter() {
        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                ActivityCompat.startPostponedEnterTransition(getActivity());
                return true;
            }
        });
    }

    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements, int current) {
        GalleryRVAdapter.PhotoViewHolder holder = (GalleryRVAdapter.PhotoViewHolder) recyclerView.findViewHolderForAdapterPosition(current);
        if (holder != null) {
            View newSharedElement = holder.imageView;
            if (newSharedElement != null) {
                String newTransitionName = ViewCompat.getTransitionName(newSharedElement);
                names.clear();
                names.add(newTransitionName);
                sharedElements.clear();
                sharedElements.put(newTransitionName, newSharedElement);
            }
        }
    }

    @Override
    public void onItemClicked(int position, View view) {
        Intent intent = new Intent(getActivity(), PagerActivity.class);
        intent.putParcelableArrayListExtra(Constants.ARG_PHOTO_LIST, mPresenter.getData());
        intent.putExtra(Constants.ARG_POSITION, position);

        String transitionName = ViewCompat.getTransitionName(view);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, transitionName);
        ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
    }
}
