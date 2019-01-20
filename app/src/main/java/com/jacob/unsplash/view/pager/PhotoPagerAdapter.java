package com.jacob.unsplash.view.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jacob.unsplash.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vynnykiakiv on 3/27/18.
 */


public class PhotoPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Photo> mData = new ArrayList<>();

    public PhotoPagerAdapter(FragmentManager fm, List<Photo> mData) {
        super(fm);
        this.mData.addAll(mData);
    }

    @Override
    public Fragment getItem(int position) {
        return PhotoFragment.newInstance(mData.get(position), position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}