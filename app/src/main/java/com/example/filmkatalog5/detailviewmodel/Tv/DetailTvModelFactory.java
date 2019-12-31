package com.example.filmkatalog5.detailviewmodel.Tv;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class DetailTvModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link DetailTvModel}
     */

    private Application application;
    private Integer tvIds;

    public DetailTvModelFactory(@NonNull Application application, int tvIds) {
        super(application);
        this.application = application;
        this.tvIds = tvIds;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailTvModel.class)) {
            return (T) new DetailTvModel(application, tvIds);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
