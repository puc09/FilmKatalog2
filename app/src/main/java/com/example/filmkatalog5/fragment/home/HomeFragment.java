package com.example.filmkatalog5.fragment.home;


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
import com.example.filmkatalog5.fragment.film.FilmListFragment;
import com.example.filmkatalog5.fragment.tv.TVListFragment;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ViewPager vwPager;
    private TabLayout tabLayout;
    private ViewPajerAdapter pagerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vwPager = view.findViewById(R.id.vw_pajer);
        tabLayout = view.findViewById(R.id.tablay);
        setTabLayout();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(vwPager);
            }
        });

    }

    private void setTabLayout() {
        String movies = getResources().getString(R.string.tab_movies);
        String tv_series = getResources().getString(R.string.tab_tv);
        pagerAdapter = new ViewPajerAdapter(getChildFragmentManager());
        pagerAdapter.join(new FilmListFragment(), movies);
        pagerAdapter.join(new TVListFragment(), tv_series);

        vwPager.setOffscreenPageLimit(2);
        vwPager.setAdapter(pagerAdapter);

        vwPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vwPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pagerAdapter != null) {
            pagerAdapter.notifyDataSetChanged();
        }
    }

}
