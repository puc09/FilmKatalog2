package com.example.filmkatalog5.notifications;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreference {
    private static final String PREF_NAME = "filmReferences";
    private static final String DAILY_REMINDER = "dailyReminder";
    private static final String DAILY_NEW_RELEASE = "dailyNewRelease";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public NotificationPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public Boolean getDailyReminder() {
        return sharedPreferences.getBoolean(DAILY_REMINDER, false);
    }

    public void setDailyReminder(Boolean check) {
        editor.putBoolean(DAILY_REMINDER, check).apply();
    }

    public Boolean getNewReleaseReminder() {
        return sharedPreferences.getBoolean(DAILY_NEW_RELEASE, false);
    }

    public void setNewReleaseReminder(Boolean check) {
        editor.putBoolean(DAILY_NEW_RELEASE, check).apply();
    }

}
