package com.example.filmkatalog5.adapters.tvseriesfragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.TvDetailActivity;
import com.example.filmkatalog5.databinding.TvContentBinding;
import com.example.filmkatalog5.model.Film;

public class TvListViewHolder extends RecyclerView.ViewHolder {
    private TvContentBinding binding;

    public TvListViewHolder(@NonNull TvContentBinding itemView) {
        super(itemView.getRoot());
        this.binding = itemView;
    }

    public static TvListViewHolder create(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        TvContentBinding tvContentBinding = TvContentBinding.inflate(inflater, parent, false);
        return new TvListViewHolder(tvContentBinding);

    }

    public void ikatPd(final Film film) {
        binding.setFilm(film);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TvDetailActivity.class);
                intent.putExtra(TvDetailActivity.DETAIL_TV, film);
                v.getContext().startActivity(intent);
            }
        });
        binding.executePendingBindings();
    }
}
