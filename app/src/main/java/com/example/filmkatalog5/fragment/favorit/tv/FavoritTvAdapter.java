package com.example.filmkatalog5.fragment.favorit.tv;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.adapters.tvseriesfragment.TvListViewHolder;
import com.example.filmkatalog5.model.Film;

import java.util.List;

public class FavoritTvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Film> tvList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TvListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film series = tvList.get(position);
        if (series != null) {
            ((TvListViewHolder) holder).ikatPd(series);
        } else {
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return tvList != null ? tvList.size() : 0;
    }

    public void setTvList(List<Film> list) {
        this.tvList = list;
        notifyDataSetChanged();

    }
}
