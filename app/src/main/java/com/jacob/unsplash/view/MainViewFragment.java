package com.jacob.unsplash.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacob.unsplash.R;
import com.jacob.unsplash.model.Photo;

import java.util.List;


/**
 * Created by vynnykiakiv on 3/23/18.
 */

public class MainViewFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = MainViewFragment.class.getName();

    public static MainViewFragment newInstance() {
        return new MainViewFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.button).setOnClickListener(this);
    }

    public void setList(List<Photo> photoList) {

    }

    @Override
    public void onClick(View view) {
        load();
    }

    private void load() {

    }

    public void onFail(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }
}
