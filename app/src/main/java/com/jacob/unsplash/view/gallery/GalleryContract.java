package com.jacob.unsplash.view.gallery;

import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.view.BaseView;

import java.util.List;

/**
 * Created by vynnykiakiv on 6/2/18.
 */

public interface GalleryContract {
    interface View extends BaseView<Presenter> {
        void showProgressBar(boolean isEnabled);

        void onSearchSucceeded(List<Photo> photoList);

        void onSearchFailed(String message);
    }

    interface Presenter {
        void onPhotoClicked(int position, android.view.View view);

        void onSearch(String query);

        void onDestroy();
    }
}
