package com.example.myfavouritesapp.viewmodels;

import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfavouritesapp.model.Film;

import java.util.ArrayList;

import static com.example.myfavouritesapp.database.DatabaseContract.AUTHORITY;
import static com.example.myfavouritesapp.database.DatabaseContract.FaveDbColumns.CONTENT_URI;
import static com.example.myfavouritesapp.database.DatabaseContract.FaveDbColumns.TABLE_NAME;

public class ListFilmViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Film>> listFilm = new MutableLiveData<>();
    private Context context;

    public void setListFilm() {
        Uri uri = Uri.parse("content://"+AUTHORITY+"/"+TABLE_NAME);
        ContentProviderClient client = context.getContentResolver().acquireContentProviderClient(uri);

        try {
            ArrayList<Film> filmArrayList = new ArrayList<>();
            if (client != null) {
                Cursor cursor = client.query(CONTENT_URI, null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    if (cursor.getCount() > 0) {
                        do {
                            Film film = new Film();
                            film.setTheIds((cursor.getInt(0)));
                            film.setFilmTitle(cursor.getString(1));
                            film.setOverView(cursor.getString(3));
                            film.setPosterPath(cursor.getString(7));
                            filmArrayList.add(film);
                            cursor.moveToNext();
                        } while (!cursor.isAfterLast());
                    }
                    cursor.close();
                    listFilm.postValue(filmArrayList);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<ArrayList<Film>> getListFilm() {
        return listFilm;
    }

    public void getContext(Context mContext) {
        this.context = mContext;
    }
}
