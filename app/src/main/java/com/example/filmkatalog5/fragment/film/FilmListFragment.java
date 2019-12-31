package com.example.filmkatalog5.fragment.film;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.R;
import com.example.filmkatalog5.adapters.fillmfragment.ListFilmAdapter;
import com.example.filmkatalog5.adapters.searchs.SearchFilmAdapter;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.filmkatalog5.utility.Constant.LIST_STATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmListFragment extends Fragment {
    private final String TAG = "ListFilmFragment";

    private ArrayList<Film> movieList;
    private ListFilmAdapter adapter;
    private ProgressBar pbLoad;
    private FilmListModel listModel;
    private LinearLayoutManager mlayoutManager;
    private SearchFilmAdapter searchAdapter;
    private SearchView searchFilm;
    private RecyclerView rvFilms;
    private SearchView.OnQueryTextListener searchResult = new SearchView.OnQueryTextListener() {
        Handler handler = new Handler();

        @Override
        public boolean onQueryTextSubmit(String filmQuery) {
            setMovieSearchResults(filmQuery);
            return true;
        }

        @Override
        public boolean onQueryTextChange(final String newText) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setMovieSearchResults(newText);
                }
            }, 300);
            return false;
        }
    };

    public FilmListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFilms = view.findViewById(R.id.rv_films);
        pbLoad = view.findViewById(R.id.film_loading);
        rvFilms.setHasFixedSize(true);
        movieList = new ArrayList<>();

        mlayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFilms.setLayoutManager(mlayoutManager);
        adapter = new ListFilmAdapter(listModel, movieList);
        rvFilms.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIST_STATE)) {
                ArrayList<Film> list = savedInstanceState.getParcelableArrayList(LIST_STATE);
                adapter.setListFilm(list);
            }
        }

        listModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmListModel.class);
        listModel.getFilm();
        listModel.isLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (isLoading) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }
        });

        listModel.getError().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        setMovies();
    }

    private void setMovies() {
        listModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> films) {
                if (films != null) {
                    adapter.setListFilm(films);
                }
            }
        });
    }

    private void showLoading() {
        pbLoad.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        pbLoad.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        movieList = adapter.getFilmList();
        if (movieList != null && !movieList.isEmpty()) {
            outState.putParcelableArrayList(LIST_STATE, movieList);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.item_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_menus);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (menuItem != null) {
            menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem menuItem) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                    listModel.getFilm();
                    setMovies();
                    return true;
                }
            });
            searchFilm = (SearchView) menuItem.getActionView();

        }
        if (searchFilm != null) {
            searchFilm.setQueryHint(getResources().getString(R.string.search_mov));
            searchFilm.setFocusable(true);
            searchFilm.clearFocus();
            searchFilm.setSearchableInfo((Objects.requireNonNull(searchManager)).getSearchableInfo(getActivity().getComponentName()));
            searchFilm.setOnQueryTextListener(searchResult);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.search_menus) {
            return false;
        }
        searchFilm.setOnQueryTextListener(searchResult);
        return super.onOptionsItemSelected(menuItem);
    }

    private void setMovieSearchResults(String filmQuery) {
        rvFilms.setHasFixedSize(true);
        movieList.clear();
        movieList = new ArrayList<>();

        mlayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFilms.setLayoutManager(mlayoutManager);
        searchAdapter = new SearchFilmAdapter(movieList, listModel);

        rvFilms.setAdapter(searchAdapter);
        listModel.getSearchFilm(filmQuery);
        listModel.getSearchResult().observe(getViewLifecycleOwner(), new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> films) {
                if (films != null) {
                    searchAdapter.setSearchList(films);
                }
            }
        });
    }


}
