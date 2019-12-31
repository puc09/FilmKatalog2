package com.example.myfavouritesapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfavouritesapp.adapter.FilmAdapter;
import com.example.myfavouritesapp.model.Film;
import com.example.myfavouritesapp.viewmodels.ListFilmViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvFav;
    private FilmAdapter adapter;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFav = findViewById(R.id.rv_fav);
        emptyView = findViewById(R.id.empty_fav);
        rvFav.setHasFixedSize(true);
        ArrayList<Film> listFilm = new ArrayList<>();

        LinearLayoutManager mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvFav.setLayoutManager(mManager);

        adapter = new FilmAdapter(this, listFilm);
        rvFav.setAdapter(adapter);

        ListFilmViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ListFilmViewModel.class);
        viewModel.getContext(this);
        viewModel.setListFilm();
        viewModel.getListFilm().observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> films) {
                if (films.isEmpty()) {
                    rvFav.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);

                } else {
                    rvFav.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    adapter.setListFilm(films);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_change_lang) {
            Intent setting = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(setting);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
