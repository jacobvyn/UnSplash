package com.jacob.unsplash;

import com.jacob.unsplash.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vynnykiakiv on 3/26/18.
 */

public class MockDataBase {
    private final ArrayList<Photo> mInMemoryDB = new ArrayList<Photo>();
    private static MockDataBase mInstance;

    private MockDataBase() {
    }

    public static MockDataBase getInstance() {
        if (mInstance == null) {
            mInstance = new MockDataBase();
        }
        return mInstance;
    }

    public boolean isDataAvailable() {
        return mInMemoryDB.size() > 0;
    }

    public ArrayList<Photo> getData() {
        return new ArrayList<>(mInMemoryDB);
    }

    public void setData(List<Photo> list) {
        mInMemoryDB.clear();
        mInMemoryDB.addAll(list);
    }
}
