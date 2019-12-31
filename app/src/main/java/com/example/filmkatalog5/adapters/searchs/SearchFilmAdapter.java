package com.example.filmkatalog5.adapters.searchs;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.adapters.fillmfragment.ListFilmViewHolder;
import com.example.filmkatalog5.fragment.film.FilmListModel;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;

public class SearchFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Film> listFilm;
    private FilmListModel viewModel;

    public SearchFilmAdapter(ArrayList<Film> list, FilmListModel model) {
        this.listFilm = list;
        this.viewModel = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ListFilmViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Film film = listFilm.get(position);
        ((ListFilmViewHolder) holder).ikatKe(film);
    }

    @Override
    public int getItemCount() {
        return listFilm.size();
    }

    public ArrayList<Film> getSearchList() {
        return listFilm;
    }

    public void setSearchList(ArrayList<Film> list) {
        listFilm.clear();
        listFilm.addAll(list);
        notifyDataSetChanged();
    }
}
