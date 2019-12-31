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
import com.example.filmkatalog5.detailviewmodel.Film.ModelDetailFilm;
import com.example.filmkatalog5.detailviewmodel.Film.ModelDetailFilmFactory;
import com.example.filmkatalog5.fragment.favorit.film.FilmFavoritVM;
import com.example.filmkatalog5.model.Cast;
import com.example.filmkatalog5.model.Film;
import com.example.filmkatalog5.model.Genre;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.filmkatalog5.utility.Constant.IMG_URL;

public class FilmDetailActivity extends AppCompatActivity {

    public static final String DETAIL_FILM = "detail_film";
    RecyclerView rvCast;
    ProgressBar imgLoad, castLoad, filmLoad;
    ImageView mImage;
    private TextView mStory, mRating, mJudul, mWeb, mTag, mTgl, mRuntime, mGenre, mVote, Bahasa, mRev;
    private Film movies;
    private ArrayList<Cast> castsList = new ArrayList<>();
    private ArrayList<Genre> genreList = new ArrayList<>();
    private CastAdapter castAdapter;
    private Boolean isFavorit;
    private FilmFavoritVM favoritModel;
    private Observer<Film> getDataFav = new Observer<Film>() {
        @Override
        public void onChanged(final Film favoritFilm) {
            if (favoritFilm != null && favoritFilm.getMovie() != null) {
                isFavorit = true;
                setTitle(getString(R.string.favorite));
            } else {
                isFavorit = false;
            }
            invalidateOptionsMenu();
        }

    };
    private Observer<Film> getFilms = new Observer<Film>() {
        @Override
        public void onChanged(final Film movies) {
            assignData(movies);
        }
    };
    private Observer<Boolean> getMessage = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if (aBoolean) {
                Toast.makeText(FilmDetailActivity.this, getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    };
    private Observer<Boolean> load = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isLoading) {
            if (isLoading) {
                filmLoad.setVisibility(View.VISIBLE);
            } else {
                filmLoad.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_film_depan);

        ActionBar ab = this.getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayShowHomeEnabled(true);

        }

        movies = getIntent().getParcelableExtra(DETAIL_FILM);

        initViews();
        initData();

        ModelDetailFilmFactory factory = new ModelDetailFilmFactory(getApplication(), movies.getTheIds());
        ModelDetailFilm modelDetailFilm = new ViewModelProvider(this, factory).get(ModelDetailFilm.class);
        modelDetailFilm.getMovies().observe(this, getFilms);
        modelDetailFilm.getError().observe(this, getMessage);
        modelDetailFilm.isLoading().observe(this, load);
        modelDetailFilm.getFavoritFilm(movies.getTheIds()).observe(this, getDataFav);
        favoritModel = new ViewModelProvider(this).get(FilmFavoritVM.class);

        isFavorit = movies.getMovie() != null;

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

        setRvCast();

    }

    private void setRvCast() {
        rvCast = findViewById(R.id.cast_rv);
        rvCast.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvCast.setLayoutManager(layoutManager);
        castAdapter = new CastAdapter(FilmDetailActivity.this, castsList);
        rvCast.setAdapter(castAdapter);
    }

    private void initData() {
        mJudul.setText(movies.getFilmTitle());
        mStory.setText(movies.getOverView());
        Bahasa.setText(movies.getOriginalLanguage());
        mTgl.setText(movies.getReleaseDate(getApplicationContext()));
        mRating.setText(String.valueOf(movies.getRating()));
        mVote.setText(String.format(getResources().getString(R.string.votes), Integer.toString(movies.getVoteCount())));

        Glide.with(FilmDetailActivity.this).load(IMG_URL + movies.getPosterPath())
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
                .into(mImage);

        setTitle(movies.getFilmTitle());
    }


    private void showLoading(Boolean state) {
        if (state) {
            filmLoad.setVisibility(View.VISIBLE);
        } else {
            filmLoad.setVisibility(View.GONE);
        }
    }


    private void assignData(Film movies) {
        if (movies != null) {
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
                Film fav = new Film();
                fav.setTheIds(movies.getTheIds());
                fav.setPosterPath(movies.getPosterPath());
                fav.setFilmTitle(movies.getFilmTitle());
                fav.setOverView(movies.getOverView());
                fav.setVoteCount(movies.getVoteCount());
                fav.setOriginalLanguage(movies.getOriginalLanguage());
                fav.setReleaseDate(movies.getReleaseDate(FilmDetailActivity.this));
                fav.setRating(movies.getRating());
                fav.setMovie(true);
                if (!isFavorit) {
                    favoritModel.insert(fav);
                    Toast.makeText(FilmDetailActivity.this, getResources().getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(R.drawable.ic_favorite_white_24dp);
                    movies.setMovie(true);
                    isFavorit = true;
                } else {
                    movies = fav;
                    favoritModel.remove(movies);
                    movies.setMovie(false);
                    Toast.makeText(FilmDetailActivity.this, getResources().getString(R.string.rem_fav), Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(R.drawable.ic_favorite_border_white_24dp);
                    isFavorit = false;
                    setTitle(movies.getFilmTitle());
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

}

