package com.example.filmkatalog5.fragment.favorit.film;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmkatalog5.databinding.FragmentFilmFavoritBinding;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFavoritFragment extends Fragment {
    private FragmentFilmFavoritBinding binding;
    private FilmFavoritVM viewModel;
    private FavoritFilmAdapter adapter;

    public FilmFavoritFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFilmFavoritBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFilmFavorit = binding.rvFilmFavorit;
        adapter = new FavoritFilmAdapter();
        rvFilmFavorit.setHasFixedSize(true);
        List<Film> list = new ArrayList<>();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvFilmFavorit.setLayoutManager(mLayoutManager);
        rvFilmFavorit.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(FilmFavoritVM.class);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getIsMovie().observe(getViewLifecycleOwner(), new Observer<List<Film>>() {
            @Override
            public void onChanged(List<Film> favoritFilms) {
                if (favoritFilms.isEmpty()) {
                    binding.rvFilmFavorit.setVisibility(View.GONE);
                    binding.emptyFilmFave.setVisibility(View.VISIBLE);
                } else {
                    binding.rvFilmFavorit.setVisibility(View.VISIBLE);
                    binding.emptyFilmFave.setVisibility(View.GONE);
                    adapter.setFavoritData(favoritFilms);
                }
            }
        });

    }

}
