package com.example.filmkatalog5;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.filmkatalog5.adapters.casts.CastAdapter;
import com.example.filmkatalog5.detailviewmodel.Tv.DetailTvModel;
import com.example.filmkatalog5.detailviewmodel.Tv.DetailTvModelFactory;
import com.example.filmkatalog5.fragment.favorit.tv.FavoritTvViewModel;
import com.example.filmkatalog5.model.Cast;
import com.example.filmkatalog5.model.Film;
import com.example.filmkatalog5.model.Genre;
import com.example.filmkatalog5.model.Networks;

import java.util.ArrayList;
import java.util.List;

import static com.example.filmkatalog5.utility.Constant.IMG_URL;

public class TvDetailActivity extends AppCompatActivity {
    public static final String DETAIL_TV = "tv_detail";
    RecyclerView rvTVCast;
    ProgressBar castvLoad, imgLoad, tvLoad;
    ImageView tImage;
    private TextView tStation, tStory, tRating, tJudul, tGenres, tWeb, tTgl, tRuntime, tVote, tBahasa, tEps, tSeas;
    private Boolean isFavorit;
    private Film seri;
    private ArrayList<Networks> netList = new ArrayList<>();
    private ArrayList<Genre> genreList = new ArrayList<>();
    private ArrayList<Cast> tvcastList = new ArrayList<>();
    private CastAdapter adapter;
    private FavoritTvViewModel tvFavModel;
    private Observer<Film> getTvDetail = new Observer<Film>() {
        @Override
        public void onChanged(Film series) {
            setData(series);
        }
    };
    private Observer<Film> getSerialFav = new Observer<Film>() {
        @Override
        public void onChanged(Film film) {
            if (film != null && film.getTv() != null) {
                isFavorit = true;
                setTitle(getString(R.string.favorite));
            } else {
                isFavorit = false;
            }
            invalidateOptionsMenu();
        }
    };
    private Observer<Boolean> showError = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean showError) {
            if (showError) {
                Toast.makeText(TvDetailActivity.this, getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Observer<Boolean> showLoading = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean loading) {
            if (loading) {
                showLoad();
            } else {
                hideLoad();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tv_depan);
        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);

        }
        seri = getIntent().getParcelableExtra(DETAIL_TV);

        initTviews();
        initDataTv();
        DetailTvModelFactory factory = new DetailTvModelFactory(getApplication(), seri.getTheIds());

        DetailTvModel detailTvModel = new ViewModelProvider(this, factory).get(DetailTvModel.class);
        tvFavModel = new ViewModelProvider(this).get(FavoritTvViewModel.class);
        detailTvModel.getSeries().observe(this, getTvDetail);
        detailTvModel.getError().observe(this, showError);
        detailTvModel.getLoading().observe(this, showLoading);
        detailTvModel.getFavoritSeries(seri.getTheIds()).observe(this, getSerialFav);


        isFavorit = seri.getTv() != null;
    }

    private void initDataTv() {
        tJudul.setText(seri.getOriginalName());
        tStory.setText(seri.getOverView());
        tBahasa.setText(seri.getOriginalLanguage());
        tTgl.setText(seri.getFirstDate(TvDetailActivity.this));
        tRating.setText(String.valueOf(seri.getRating()));
        tVote.setText(String.format(getResources().getString(R.string.votes), Integer.toString(seri.getVoteCount())));

        Glide.with(TvDetailActivity.this).load(IMG_URL + seri.getPosterPath())
                .apply(new RequestOptions()).override(250, 350)
                .error(R.drawable.empty_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        imgLoad.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imgLoad.setVisibility(View.GONE);
                        return false;
                    }
                })
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(tImage);

        setTitle(seri.getOriginalName());
    }

    private void initTviews() {
        tJudul = findViewById(R.id.tv_judul);
        tWeb = findViewById(R.id.tv_homepage);
        tStation = findViewById(R.id.network);
        tTgl = findViewById(R.id.tv_tahun);
        tRuntime = findViewById(R.id.tv_runtime);
        tImage = findViewById(R.id.gbr_films_tv);
        tRating = findViewById(R.id.tv_rating);
        tVote = findViewById(R.id.tv_vote);
        tBahasa = findViewById(R.id.tv_lang);
        tStory = findViewById(R.id.tv_sinopsis);
        castvLoad = findViewById(R.id.rv_cast_load);
        tGenres = findViewById(R.id.tv_genre);
        tEps = findViewById(R.id.lbl_total_epis);
        tSeas = findViewById(R.id.lbl_total_seas);
        imgLoad = findViewById(R.id.tv_loading);
        tvLoad = findViewById(R.id.detail_tv_loading);
        setRvTVCast();
    }


    void setRvTVCast() {
        rvTVCast = findViewById(R.id.tv_cast_rv);
        rvTVCast.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTVCast.setLayoutManager(layoutManager);
        adapter = new CastAdapter(TvDetailActivity.this, tvcastList);
        rvTVCast.setAdapter(adapter);
    }

    private void showLoad() {
        tvLoad.setVisibility(View.VISIBLE);
    }

    private void hideLoad() {
        tvLoad.setVisibility(View.GONE);
    }

    private void setData(Film series) {
        if (series != null) {
            tWeb.setText(series.getHomePage() != null ? series.getHomePage() : getResources().getString(R.string.na));
            tSeas.setText(String.format(getResources().getString(R.string.mepisode), Integer.toString(series.getSeasCount())));
            tEps.setText(String.format(getResources().getString(R.string.mseason), Integer.toString(series.getEpsCount())));

            tRuntime.setText(String.format(getString(R.string.minutes), TextUtils.join(", ", series.getEpsRuntime())));

            genreList.clear();
            genreList.addAll(series.getGenresList());
            List<String> genreName = new ArrayList<>();
            for (Genre genre : genreList) {
                genreName.add(genre.getName());
                tGenres.setText(TextUtils.join(", ", genreName));
            }

            tvcastList.clear();
            tvcastList.addAll(series.getPemain().getCasts());
            adapter.notifyDataSetChanged();
            castvLoad.setVisibility(View.GONE);

            netList.clear();
            netList.addAll(series.getNetStation());
            List<String> listNet = new ArrayList<>();
            for (Networks networks : netList) {
                listNet.add(networks.getNetName());
                tStation.setText(TextUtils.join(", ", listNet));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menus) {
        getMenuInflater().inflate(R.menu.fave_detail, menus);
        MenuItem item = menus.findItem(R.id.action_add_fave);
        if (isFavorit) {
            item.setIcon(R.drawable.ic_favorite_white_24dp);
        } else {
            item.setIcon(R.drawable.ic_favorite_border_white_24dp);
        }
        invalidateOptionsMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_add_fave: {
                Film favTv = new Film();
                favTv.setTheIds(seri.getTheIds());
                favTv.setPosterPath(seri.getPosterPath());
                favTv.setOriginalName(seri.getOriginalName());
                favTv.setOverView(seri.getOverView());
                favTv.setVoteCount(seri.getVoteCount());
                favTv.setRating(seri.getRating());
                favTv.setFirstDate(seri.getFirstDate(TvDetailActivity.this));
                favTv.setOriginalLanguage(seri.getOriginalLanguage());
                favTv.setTv(true);
                if (!isFavorit) {
                    tvFavModel.insert(favTv);
                    Toast.makeText(TvDetailActivity.this, getResources().getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(R.drawable.ic_favorite_white_24dp);
                    seri.setTv(true);
                    isFavorit = true;
                } else {
                    seri = favTv;
                    tvFavModel.remove(seri);
                    seri.setTv(false);
                    Toast.makeText(TvDetailActivity.this, getResources().getString(R.string.rem_fav), Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    isFavorit = false;
                    setTitle(seri.getOriginalName());
                }
                invalidateOptionsMenu();
                return true;
            }
            case android.R.id.home: {
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}