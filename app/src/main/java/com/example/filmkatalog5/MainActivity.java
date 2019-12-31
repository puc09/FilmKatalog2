package com.example.filmkatalog5;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.filmkatalog5.adapters.viewpager.ViewPajerAdapter;
import com.example.filmkatalog5.fragment.home.HomeFragment;
import com.example.filmkatalog5.fragment.maintab.FavoritFragment;
import com.example.filmkatalog5.utility.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private ViewPajerAdapter pagerAdapter;
    private BottomNavigationView botNav;
    private Fragment fragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.app_name);
        }
        setBottomNav();

        if (savedInstanceState == null) {
            botNav.setSelectedItemId(R.id.navigation_home);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_change_lang: {
                Intent setting = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(setting);
                return true;
            }
            case R.id.action_change_setting: {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void setBottomNav() {
        botNav = findViewById(R.id.bottom_navigation);
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.tab_container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;

                    case R.id.select_tab:
                        fragment = new FavoritFragment();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.tab_container, fragment, fragment.getClass().getSimpleName())
                                .commit();
                        return true;
                    default:
                        return false;

                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (pagerAdapter != null) {
            pagerAdapter.notifyDataSetChanged();
        }
    }
}
