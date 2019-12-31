package com.example.filmkatalog5.fragment.favorit.film;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.adapters.fillmfragment.ListFilmViewHolder;
import com.example.filmkatalog5.model.Film;

import java.util.List;


public class FavoritFilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Film> filmList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ListFilmViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = filmList.get(position);
        if (film != null) {
            ((ListFilmViewHolder) holder).ikatKe(film);

        } else {
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return filmList != null ? filmList.size() : 0;
    }

    public void setFavoritData(List<Film> list) {
        this.filmList = list;
        notifyDataSetChanged();
    }
}
