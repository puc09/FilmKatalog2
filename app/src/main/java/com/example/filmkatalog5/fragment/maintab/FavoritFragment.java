package com.example.filmkatalog5.fragment.maintab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.filmkatalog5.R;
import com.example.filmkatalog5.adapters.viewpager.ViewPajerAdapter;
import com.example.filmkatalog5.fragment.favorit.film.FilmFavoritFragment;
import com.example.filmkatalog5.fragment.favorit.tv.TvFavoritFragment;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayouts;


    public FavoritFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayouts = view.findViewById(R.id.tablayout_fave);

        setTabLayouts();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayouts.post(new Runnable() {
            @Override
            public void run() {
                tabLayouts.setupWithViewPager(viewPager);
            }
        });
    }

    private void setTabLayouts() {
        String favorit_film = getResources().getString(R.string.tab_fav_mov);
        String favorit_tv_series = getResources().getString(R.string.tab_fav_tv);
        ViewPajerAdapter adapter = new ViewPajerAdapter(getChildFragmentManager());
        adapter.join(new FilmFavoritFragment(), favorit_film);
        adapter.join(new TvFavoritFragment(), favorit_tv_series);

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

    }

}
