package com.example.myfavouritesapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.myfavouritesapp.adapter.CastAdapter;
import com.example.myfavouritesapp.model.Cast;
import com.example.myfavouritesapp.model.Film;
import com.example.myfavouritesapp.model.Genre;
import com.example.myfavouritesapp.viewmodels.DetailViewModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.myfavouritesapp.utils.Constant.IMG_URL;

public class DetailActivity extends AppCompatActivity {

    public static final String DETAIL_FILM = "detail_film";

    private ProgressBar imgLoad, castLoad, filmLoad;
    private ImageView mImage;
    private TextView mStory, mRating, mJudul, mWeb, mTag, mTgl, mRuntime, mGenre, mVote, Bahasa, mRev;
    private ArrayList<Cast> castsList = new ArrayList<>();
    private ArrayList<Genre> genreList = new ArrayList<>();
    private CastAdapter castAdapter;
    private Film movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);
        }

        movies = getIntent().getParcelableExtra(DETAIL_FILM);
        initViews();
        initIntent();

        DetailViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);
        viewModel.setMovies(movies.getTheIds());
        viewModel.getLoading().observe(this, setLoading);
        viewModel.getMovies().observe(this, new Observer<Film>() {
            @Override
            public void onChanged(Film film) {
                setData(film);
            }
        });
    }

    private void initViews() {
        mJudul = findViewById(R.id.judul);
        mWeb = findViewById(R.id.homepage);
        mTgl = findViewById(R.id.tahun);
        mRuntime = findViewById(R.id.runtime);
        mImage = findViewById(R.id.gbr_films);
        mRating = findViewById(R.id.rating);
        mVote = findViewById(R.id.vote);
        Bahasa = findViewById(R.id.lang);
        mRev = findViewById(R.id.revenue);
        castLoad = findViewById(R.id.rv_cast_load);
        mGenre = findViewById(R.id.film_genre);
        mStory = findViewById(R.id.sinopsis);
        imgLoad = findViewById(R.id.progressbar);
        filmLoad = findViewById(R.id.filmDetail_loading);
        mWeb = findViewById(R.id.homepage);
        mTag = findViewById(R.id.tagline);

        setCastRv();
    }

    private void initIntent() {
        mJudul.setText(movies.getFilmTitle());
        mStory.setText(movies.getOverView());

        Glide.with(DetailActivity.this).load(IMG_URL + movies.getPosterPath())
                .apply(new RequestOptions()).override(250, 300)
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
                .into(mImage);

        setTitle(movies.getFilmTitle());
    }

    private void setCastRv() {
        RecyclerView rvCast = findViewById(R.id.cast_rv);
        rvCast.setHasFixedSize(true);
        LinearLayoutManager lManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCast.setLayoutManager(lManager);

        castAdapter = new CastAdapter(DetailActivity.this, castsList);
        rvCast.setAdapter(castAdapter);
    }

    private void setData(Film movies) {
        if (movies != null) {
            Bahasa.setText(movies.getOriginalLanguage());
            mTgl.setText(movies.getReleaseDate(getApplicationContext()));
            mRating.setText(String.valueOf(movies.getRating()));
            mVote.setText(String.format(getResources().getString(R.string.votes), Integer.toString(movies.getVoteCount())));

            int hrs = movies.getRunTime() / 60;
            int min = movies.getRunTime() - 60;
            mRuntime.setText(String.format(getResources().getString(R.string.hours), hrs, min));
            mWeb.setText(movies.getHomePage() != null ? movies.getHomePage() : getResources().getString(R.string.na));
            mTag.setText(movies.getTagLine() != null ? movies.getTagLine() : getResources().getString(R.string.na));
            mRev.setText(NumberFormat.getCurrencyInstance(Locale.US).format(movies.getFilmIncome()));

            genreList.clear();
            genreList.addAll(movies.getGenresList());
            List<String> genreName = new ArrayList<>();
            for (Genre genre : genreList) {
                genreName.add(genre.getName());
                mGenre.setText(TextUtils.join(", ", genreName));
            }
            castsList.clear();
            castLoad.setVisibility(View.GONE);
            castsList.addAll(movies.getPemain().getCasts());
            castAdapter.notifyDataSetChanged();
        }

    }

    private void showLoading() {
        filmLoad.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        filmLoad.setVisibility(View.GONE);
    }

    private Observer<Boolean> setLoading = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isLoading) {
            if (isLoading) {
                showLoading();
            } else {
                hideLoading();
            }
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
