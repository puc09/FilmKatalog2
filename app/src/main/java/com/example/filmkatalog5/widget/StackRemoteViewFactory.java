package com.example.filmkatalog5.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.filmkatalog5.R;
import com.example.filmkatalog5.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.example.filmkatalog5.data.database.DatabaseContract.FaveDbColumns.CONTENT_URI;
import static com.example.filmkatalog5.utility.Constant.IMG_URL_WIDGET;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Bitmap> widgetImg = new ArrayList<>();
    private List<Film> listFilm = new ArrayList<>();
    private Cursor cursor;
    private Context context;

    public StackRemoteViewFactory(Context context, Intent intent) {
        this.context = context;
        int widgetImgId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while ((cursor).moveToNext()) {
                listFilm.add(new Film(cursor));
            }
        }
        Binder.restoreCallingIdentity(identityToken);

        for (Film films : listFilm) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context).asBitmap().load(IMG_URL_WIDGET + films.getPosterPath()).submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                widgetImg.add(bitmap);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return widgetImg.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (getCount() > 0) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_items);
            remoteViews.setImageViewBitmap(R.id.img_widget, widgetImg.get(position));

            Bundle bundle = new Bundle();
            bundle.putInt(FavouriteWidget.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            remoteViews.setOnClickFillInIntent(R.id.img_widget, fillInIntent);
            remoteViews.setTextViewText(R.id.txt_widget, listFilm.get(position).getFilmTitle());
            return remoteViews;
        } else {
            return new RemoteViews(context.getPackageName(), R.layout.widget_items);
        }
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return cursor.moveToPosition(i) ? cursor.getLong(0) : i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
