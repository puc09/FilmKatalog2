package com.example.filmkatalog5.adapters.binding;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.filmkatalog5.R;
import com.example.filmkatalog5.utility.Dp2Px;

import static com.example.filmkatalog5.utility.Constant.IMG_URL;

public class BindingAdapters {

    @BindingAdapter(value = {"imgPath", "progressbar"})
    public static void bindImage(ImageView imageView, String imagePath, final ProgressBar progressBar) {
        Glide.with(imageView.getContext()).load(IMG_URL + imagePath)
                .apply(new RequestOptions()).override(250, 350)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @BindingAdapter(value = {"posterPath", "progressbar"})
    public static void bindPoster(ImageView poster, String posterPath, final ProgressBar progressBar) {
        Glide.with(poster.getContext()).load(IMG_URL + posterPath).apply(new RequestOptions().transform(new CenterCrop()
                , new RoundedCorners(Dp2Px.dpToPx(poster.getContext(), 8))))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.empty_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(poster);
    }
}
