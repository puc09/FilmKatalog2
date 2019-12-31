package com.example.filmkatalog5.notifications.newrelease;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.adapters.fillmfragment.ListFilmViewHolder;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;

public class ReleaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Film> listFilm;

    public ReleaseAdapter(Context context, ArrayList<Film> list) {
        this.listFilm = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ListFilmViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film films = listFilm.get(position);
        ((ListFilmViewHolder) holder).ikatKe(films);

    }

    @Override
    public int getItemCount() {
        return listFilm != null ? listFilm.size() : 0;
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
