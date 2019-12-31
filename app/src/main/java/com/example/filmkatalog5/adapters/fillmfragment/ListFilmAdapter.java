package com.example.filmkatalog5.adapters.fillmfragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.fragment.film.FilmListModel;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;

public class ListFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Film> listFilm;
    private FilmListModel model;

    public ListFilmAdapter(FilmListModel model, ArrayList<Film> list) {
        this.model = model;
        this.listFilm = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ListFilmViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = listFilm.get(position);
        ((ListFilmViewHolder) holder).ikatKe(film);
    }

    @Override
    public int getItemCount() {
        return listFilm.size();
    }

    public ArrayList<Film> getFilmList() {
        return listFilm;
    }

    public void setListFilm(ArrayList<Film> list) {
        listFilm.clear();
        listFilm.addAll(list);
        notifyDataSetChanged();
    }
}
