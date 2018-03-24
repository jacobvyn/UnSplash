package com.jacob.unsplash.model;

/**
 * Created by vynnykiakiv on 3/24/18.
 */

public class Photo {
    private Urls mUrls;

    public Photo(Urls mUrls) {
        this.mUrls = mUrls;
    }

    public String getThumb() {
        return mUrls.getThumb();
    }

    public String getSmall() {
        return mUrls.getSmall();
    }

    public String getRegular() {
        return mUrls.getRegular();
    }

    public String getFull() {
        return mUrls.getFull();
    }

    public String getRaw() {
        return mUrls.getRaw();
    }
}
