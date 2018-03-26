package com.jacob.unsplash.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.view.ItemViewFragment;

import java.util.ArrayList;

/**
 * Created by vynnykiakiv on 3/27/18.
 */


public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Photo> mData = new ArrayList<>();

    public PhotoPagerAdapter(FragmentManager fm, ArrayList<Photo> mData) {
        super(fm);
        this.mData.addAll(mData);
    }

    @Override
    public Fragment getItem(int position) {
        return ItemViewFragment.newInstance(mData.get(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}