package com.example.filmkatalog5.adapters.tvseriesfragment;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.fragment.tv.TvListModel;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;

public class TvListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Film> listSeries;

    public TvListAdapter(ArrayList<Film> list, TvListModel model) {
        this.listSeries = list;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TvListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film film = listSeries.get(position);
        ((TvListViewHolder) holder).ikatPd(film);
    }

    @Override
    public int getItemCount() {
        return listSeries.size();
    }

    public ArrayList<Film> getTvList() {
        return listSeries;
    }

    public void setTvList(ArrayList<Film> tvList) {
        listSeries.clear();
        listSeries.addAll(tvList);
        notifyDataSetChanged();
    }
}
