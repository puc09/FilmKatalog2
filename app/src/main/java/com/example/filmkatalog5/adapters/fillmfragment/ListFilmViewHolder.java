package com.example.filmkatalog5.adapters.fillmfragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.FilmDetailActivity;
import com.example.filmkatalog5.databinding.ListContentBinding;
import com.example.filmkatalog5.model.Film;

public class ListFilmViewHolder extends RecyclerView.ViewHolder {
    private ListContentBinding listContentBinding;

    public ListFilmViewHolder(@NonNull ListContentBinding itemView) {
        super(itemView.getRoot());
        this.listContentBinding = itemView;
    }

    public static ListFilmViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ListContentBinding binding = ListContentBinding.inflate(inflater, parent, false);
        return new ListFilmViewHolder(binding);
    }

    public void ikatKe(final Film film) {
        listContentBinding.setFilm(film);

        listContentBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kirim = new Intent(view.getContext(), FilmDetailActivity.class);
                kirim.putExtra(FilmDetailActivity.DETAIL_FILM, film);
                view.getContext().startActivity(kirim);

            }
        });
        listContentBinding.executePendingBindings();
    }
}
