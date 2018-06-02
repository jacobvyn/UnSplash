package com.jacob.unsplash.view.pager;

import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.view.BaseView;

import java.util.List;

public interface PagerContract {
    interface View extends BaseView<Presenter> {
        void showMessage(int messageId);
    }

    interface Presenter {
        List<Photo> getData();

        int getCurrentPos();

        void setCurrentPos(int position);

        void onShareClicked();

        void onDownloadClicked();
    }
}
