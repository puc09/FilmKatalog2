package com.example.filmkatalog5.detailviewmodel.Film;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ModelDetailFilmFactory extends ViewModelProvider.AndroidViewModelFactory {
    /**
     * Creates a {@code AndroidViewModelFactory}
     *
     * @param application an application to pass in {@link ModelDetailFilm}
     */
    private Application application;
    private int filmIds;

    public ModelDetailFilmFactory(@NonNull Application application, int id) {
        super(application);
        this.application = application;
        this.filmIds = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ModelDetailFilm.class)) {
            return (T) new ModelDetailFilm(application, filmIds);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
