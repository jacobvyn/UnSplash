package com.jacob.unsplash.view.gallery;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

import com.jacob.unsplash.R;

class ImprovedGridLayoutManager extends GridLayoutManager {

    private int childViewId = R.id.item_image_view;

    public ImprovedGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public void setChildViewId(int childViewId) {
        this.childViewId = childViewId;
    }

    public int getFirstVisiblePosition() {
        Object tag = getChildAt(0).findViewById(childViewId).getTag();
        if (tag instanceof Integer) {
            return (Integer) tag;
        }
        return 0;
    }

    public int getLastVisiblePosition() {
        Object tag = getChildAt(getChildCount() - 1).findViewById(childViewId).getTag();
        if (tag instanceof Integer) {
            return (Integer) tag;
        }
        return 0;
    }
}
