package com.example.filmkatalog5.utility;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.filmkatalog5.R;
import com.example.filmkatalog5.notifications.DailyReceiver;
import com.example.filmkatalog5.notifications.NotificationPreference;
import com.example.filmkatalog5.notifications.TodayFilmReceiver;

import static com.example.filmkatalog5.utility.Constant.TYPE_DAILY;
import static com.example.filmkatalog5.utility.Constant.TYPE_NEW_RELEASE;

public class SettingsActivity extends AppCompatActivity {

    private Switch btn_dailyReminder;
    private Switch btn_releaseReminder;
    private DailyReceiver dailyReceiver;
    private TodayFilmReceiver todayFilmReceiver;
    private NotificationPreference notificationPreference;
    private Boolean isDaily, isNewDaily;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        btn_dailyReminder = findViewById(R.id.notif_daily);
        btn_releaseReminder = findViewById(R.id.notif_release);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        notificationPreference = new NotificationPreference(this);
        dailyReceiver = new DailyReceiver();
        todayFilmReceiver = new TodayFilmReceiver();

        isDaily = !notificationPreference.getDailyReminder();
        isNewDaily = !notificationPreference.getNewReleaseReminder();
        setSwitch();

        setDailyReminder();
        setNewReleaseReminder();

    }

    private void setSwitch() {
        btn_dailyReminder.setOnCheckedChangeListener(null);
        if (isDaily) {
            btn_dailyReminder.setChecked(false);
        } else {
            btn_dailyReminder.setChecked(true);

        }
        btn_releaseReminder.setOnCheckedChangeListener(null);
        if (isNewDaily) {
            btn_releaseReminder.setChecked(false);
        } else {
            btn_releaseReminder.setChecked(true);
        }
    }

    private void setDailyReminder() {
        btn_dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton btnView, boolean check) {
                if (check) {
                    isDaily = true;
                    notificationPreference.setDailyReminder(isDaily);
                    dailyRemIsOn();
                } else {
                    isDaily = false;
                    notificationPreference.setDailyReminder(isDaily);
                    dailyRemIsOff();

                }
            }
        });
    }

    private void setNewReleaseReminder() {
        btn_releaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    isNewDaily = true;
                    notificationPreference.setNewReleaseReminder(isNewDaily);
                    newReleaseIsOn();
                } else {
                    isNewDaily = false;
                    notificationPreference.setNewReleaseReminder(isNewDaily);
                    newReleaseIsOff();
                }
            }
        });
    }

    private void dailyRemIsOn() {
        String time = "07:00";
        String message = getResources().getString(R.string.daily_reminders);
        dailyReceiver.setRepeatingAlarm(SettingsActivity.this, TYPE_DAILY, time, message);
    }

    private void dailyRemIsOff() {
        dailyReceiver.cancelAlarm(SettingsActivity.this);
    }


    private void newReleaseIsOn() {
        String time = "08:00";
        final String message = getResources().getString(R.string.release_reminder);
        todayFilmReceiver.setRepeatingAlarm(SettingsActivity.this, TYPE_NEW_RELEASE, time, message);

    }

    private void newReleaseIsOff() {
        todayFilmReceiver.cancelAlarm(SettingsActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

}