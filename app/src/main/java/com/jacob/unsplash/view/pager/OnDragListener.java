package com.jacob.unsplash.view.pager;

interface OnDragListener {
    void onStartDrag();

    void onDragUpdate(float newTopLeftY);

    boolean onStopDrag(float newTopLeftY);
}
