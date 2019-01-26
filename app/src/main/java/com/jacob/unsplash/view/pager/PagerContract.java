package com.jacob.unsplash.view.pager;

import com.jacob.unsplash.model.Photo;
import com.jacob.unsplash.view.BaseView;

import java.io.File;
import java.util.List;

public interface PagerContract {
    interface View extends BaseView<Presenter> {
        void showMessage(int messageId);

        void onSaveSuccess(File file);

        void setData(List<Photo> photoList, int currentPos);
    }

    interface Presenter {

        void setCurrentPos(int position);

        void onDownloadClicked();

        void release();

        String getCurrentUrl();

        void start();
    }
}
