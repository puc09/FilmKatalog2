package com.example.filmkatalog5.fragment.tv;


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
import com.example.filmkatalog5.adapters.searchs.SearchTvAdapter;
import com.example.filmkatalog5.adapters.tvseriesfragment.TvListAdapter;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.filmkatalog5.utility.Constant.LIST_STATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVListFragment extends Fragment {
    public static final String TAG = "tvListFragment";
    private RecyclerView rvTivi;
    private ArrayList<Film> listSeries;
    private TvListAdapter tvListAdapters;
    private ProgressBar loading;
    private LinearLayoutManager lmManager;
    private TvListModel listModel;
    private SearchTvAdapter searchTvAdapter;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    private Observer<Boolean> loadView = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean loading) {
            if (loading) {
                showLoading();
            } else {
                hideLoading();
            }
        }
    };
    private Observer<Boolean> errorMsg = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean isError) {
            if (isError) {
                Toast.makeText(getActivity(), getResources().getString(R.string.internet), Toast.LENGTH_SHORT).show();
            }
        }
    };
    private SearchView.OnQueryTextListener tvListener = new SearchView.OnQueryTextListener() {
        Handler handler = new Handler();

        @Override
        public boolean onQueryTextSubmit(String query) {
            setTvSearchResult(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(final String newText) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setTvSearchResult(newText);
                }
            }, 300);
            return false;
        }
    };

    public TVListFragment() {
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
        return inflater.inflate(R.layout.fragment_tvlist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTivi = view.findViewById(R.id.rv_tv);
        loading = view.findViewById(R.id.tv_loading);

        rvTivi.setHasFixedSize(true);
        listSeries = new ArrayList<>();

        lmManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTivi.setLayoutManager(lmManager);
        tvListAdapters = new TvListAdapter(listSeries, listModel);

        rvTivi.setAdapter(tvListAdapters);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIST_STATE)) {
                ArrayList<Film> list = savedInstanceState.getParcelableArrayList(LIST_STATE);
                tvListAdapters.setTvList(list);
            }
        }
        listModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvListModel.class);
        listModel.getSeries();
        listModel.isLoading().observe(getViewLifecycleOwner(), loadView);
        listModel.getError().observe(getViewLifecycleOwner(), errorMsg);

    }

    private void setSeries() {
        listModel.getTvSeries().observe(getViewLifecycleOwner(), new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> films) {
                if (films != null) {
                    tvListAdapters.setTvList(films);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setSeries();
    }

    private void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listSeries = tvListAdapters.getTvList();
        if (listSeries != null && !listSeries.isEmpty()) {
            outState.putParcelableArrayList(LIST_STATE, listSeries);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
                    listModel.getSeries();
                    setSeries();
                    return true;
                }
            });
            searchView = (SearchView) menuItem.getActionView();
        }
        if (searchView != null) {
            searchView.setQueryHint(getResources().getString(R.string.search_tv));
            searchView.requestFocus();
            searchView.setFocusable(true);
            searchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnQueryTextListener(tvListener);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_menus) {
            return false;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void setTvSearchResult(String tvQuery) {
        rvTivi.setHasFixedSize(true);
        listSeries.clear();
        listSeries = new ArrayList<>();
        lmManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        rvTivi.setLayoutManager(lmManager);
        searchTvAdapter = new SearchTvAdapter(listSeries, listModel);

        rvTivi.setAdapter(searchTvAdapter);

        listModel.getSearchTv(tvQuery);
        listModel.getSearchResults().observe(this, new Observer<ArrayList<Film>>() {
            @Override
            public void onChanged(ArrayList<Film> films) {
                searchTvAdapter.setSearchList(films);
            }
        });
    }
}
