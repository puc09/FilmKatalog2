package com.example.filmkatalog5.fragment.favorit.tv;


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

import com.example.filmkatalog5.databinding.FragmentTvFavoritBinding;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavoritFragment extends Fragment {
    private FragmentTvFavoritBinding binding;
    private FavoritTvViewModel viewModel;
    private FavoritTvAdapter adapter;
    private List<Film> listTv;

    public TvFavoritFragment() {
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
        binding = FragmentTvFavoritBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvTvFav = binding.rvTvFavorit;
        rvTvFav.setHasFixedSize(true);
        adapter = new FavoritTvAdapter();

        listTv = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTvFav.setLayoutManager(layoutManager);
        rvTvFav.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(FavoritTvViewModel.class);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getTvListFav().observe(getViewLifecycleOwner(), new Observer<List<Film>>() {
            @Override
            public void onChanged(List<Film> films) {
                if (films.isEmpty()) {
                    binding.emptyTvFave.setVisibility(View.VISIBLE);
                    binding.rvTvFavorit.setVisibility(View.GONE);
                } else {
                    binding.emptyTvFave.setVisibility(View.GONE);
                    binding.rvTvFavorit.setVisibility(View.VISIBLE);
                    adapter.setTvList(films);
                }
            }
        });

    }

}
