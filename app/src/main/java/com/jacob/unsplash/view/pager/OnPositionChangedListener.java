package com.jacob.unsplash.view.pager;

interface OnPositionChangedListener {
    void onYChanged(float newTopLeftY);

    boolean onDrop(float newTopLeftY);

    void onStartDrag();
}
