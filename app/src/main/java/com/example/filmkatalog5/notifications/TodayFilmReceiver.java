package com.example.filmkatalog5.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.filmkatalog5.BuildConfig;
import com.example.filmkatalog5.R;
import com.example.filmkatalog5.model.ApiResponse;
import com.example.filmkatalog5.model.Film;
import com.example.filmkatalog5.notifications.newrelease.NewReleaseActivity;
import com.example.filmkatalog5.servis.ApiClient;
import com.example.filmkatalog5.servis.RestClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.filmkatalog5.utility.Constant.BASE_URL;
import static com.example.filmkatalog5.utility.Constant.EXTRA_MESSAGE;
import static com.example.filmkatalog5.utility.Constant.EXTRA_TYPE;
import static com.example.filmkatalog5.utility.Constant.GROUP_RELEASE;
import static com.example.filmkatalog5.utility.Constant.LANGUANGE;

public class TodayFilmReceiver extends BroadcastReceiver {
    public static final String TAG = TodayFilmReceiver.class.getSimpleName();
    public static final String CHANNEL_ID = "Channel 2";
    public static final String CHANNEL_NAME = "KatalogFilmChannel";
    private static int MAX_NOTIF = 8;
    private static int NOTIFICATION_ID = 202;
    private static ArrayList<Film> listFilm = new ArrayList<>();


    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String currentDate = dateFormat.format(date);

        Call<ApiResponse> panggil = RestClient.getClient(BASE_URL).create(ApiClient.class).getNewMovies(BuildConfig.THE_MOVIE_DATABASE_API_KEY, LANGUANGE, currentDate, currentDate);
        panggil.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                listFilm = response.body().getFilms();
                int notifId = 1;
                for (Film film : listFilm) {
                    if (film.getReleaseDate().equals(currentDate)) {
                        sendNotification(context, film, film.getFilmTitle(), film.getTheIds(), notifId, listFilm);
                        notifId++;
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });

    }

    private void sendNotification(Context context, Film films, String title, int id, int notifId, ArrayList<Film> listFilm) {
        List<Integer> notif = new ArrayList<>();
        notif.add(notifId);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(context, NewReleaseActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder;

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .setBigContentTitle(context.getResources().getString(R.string.release_today))
                .setSummaryText(context.getResources().getString(R.string.more_mov));

        for (int i = 0; i < listFilm.size(); i++) {
            inboxStyle.addLine(listFilm.get(i).getFilmTitle());
        }

        builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(notif.size() + context.getResources().getString(R.string.new_mov))
                .setContentText(context.getResources().getString(R.string.release, title))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setSmallIcon(R.mipmap.ic_launcher_k5)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setNumber(notifId)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setGroup(GROUP_RELEASE)
                .setGroupSummary(true)
                .setStyle(inboxStyle)
                .setSound(alarmSound);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});

            builder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(notif.size(), notification);
        }
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message) {
        String TIME_FORMAT = "HH:mm";
        if (isDateInvalid(time, TIME_FORMAT)) return;
        int delay = 0;
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TodayFilmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_MESSAGE, message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + delay,
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        delay += 5000;
    }

    public Boolean isDateInvalid(String date, String format) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
