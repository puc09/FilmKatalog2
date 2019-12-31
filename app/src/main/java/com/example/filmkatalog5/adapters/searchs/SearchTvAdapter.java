package com.example.filmkatalog5.adapters.searchs;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.adapters.tvseriesfragment.TvListViewHolder;
import com.example.filmkatalog5.fragment.tv.TvListModel;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;

public class SearchTvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Film> listSeries;
    private TvListModel tvListModel;

    public SearchTvAdapter(ArrayList<Film> list, TvListModel model) {
        this.listSeries = list;
        this.tvListModel = model;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TvListViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Film films = listSeries.get(position);
        ((TvListViewHolder) holder).ikatPd(films);
    }

    @Override
    public int getItemCount() {
        return listSeries.size();
    }

    public ArrayList<Film> getSearchList() {
        return listSeries;
    }

    public void setSearchList(ArrayList<Film> list) {
        listSeries.clear();
        listSeries.addAll(list);
        notifyDataSetChanged();
    }
}
