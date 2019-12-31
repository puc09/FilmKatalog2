package com.example.filmkatalog5.notifications.newrelease;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.R;
import com.example.filmkatalog5.model.Film;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewReleaseActivity extends AppCompatActivity {
    private ReleaseAdapter adapter;
    private RecyclerView rvNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_release);
        rvNew = findViewById(R.id.rv_listnew);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            setTitle(getResources().getString(R.string.release_today));
            ab.setDisplayHomeAsUpEnabled(true);
        }
        setRvNew();

        ReleaseViewModel listModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ReleaseViewModel.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String currentDate = dateFormat.format(date);

        listModel.setListNew(currentDate);
        listModel.getNewRelease().observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> films) {
                for (Film film : films) {
                    if (film.getReleaseDate().equals(currentDate)) {
                        adapter.setListFilm(films);
                    }
                }
            }
        });
    }

    private void setRvNew() {
        rvNew.setHasFixedSize(true);
        ArrayList<Film> listNew = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNew.setLayoutManager(layoutManager);
        adapter = new ReleaseAdapter(this, listNew);
        rvNew.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
