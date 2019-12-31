package com.example.myfavouritesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myfavouritesapp.DetailActivity;
import com.example.myfavouritesapp.R;
import com.example.myfavouritesapp.model.Film;
import com.example.myfavouritesapp.utils.DptoPx;

import java.util.ArrayList;
import java.util.List;

import static com.example.myfavouritesapp.utils.Constant.IMG_URL;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {
    private ArrayList<Film> listFilm;
    private Context context;

    public FilmAdapter(Context context, ArrayList<Film> list) {
        this.context = context;
        this.listFilm = list;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_content, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilmViewHolder holder, int position) {
        final Film film = listFilm.get(position);
        holder.title.setText(film.getFilmTitle());
        holder.over.setText(film.getOverView());

        Glide.with(holder.itemView.getContext()).load(IMG_URL + film.getPosterPath())
                .apply(new RequestOptions()
                        .transform(new CenterCrop(), new RoundedCorners(DptoPx.dpToPx(holder.itemView.getContext(), 8))))
                .override(150, 200)
                .error(R.drawable.empty_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgPoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.DETAIL_FILM, film);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listFilm == null ? 0 : listFilm.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView title, over;
        ProgressBar progressBar;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.gbr_films);
            title = itemView.findViewById(R.id.judul);
            over = itemView.findViewById(R.id.sinop);
            progressBar = itemView.findViewById(R.id.pb_loading);
        }

    }

    public ArrayList<Film> getFilmList() {
        return listFilm;
    }

    public void setListFilm(List<Film> filmList) {
        listFilm.clear();
        listFilm.addAll(filmList);
        notifyDataSetChanged();
    }


}
