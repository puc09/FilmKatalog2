package com.example.myfavouritesapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.myfavouritesapp.R;
import com.example.myfavouritesapp.model.Cast;

import java.util.ArrayList;

import static com.example.myfavouritesapp.utils.Constant.PROFILE_URL;


public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private ArrayList<Cast> castList;

    public CastAdapter(Context konteks, ArrayList<Cast> list){
        this.castList=list;
    }

    @NonNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_list,parent,false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CastAdapter.CastViewHolder holder, int position) {
        Cast casts = castList.get(position);

        holder.pemain.setText(casts.getName());
        holder.castEmpty.setImageResource(R.drawable.empty_image);


        Glide.with(holder.itemView.getContext()).load(PROFILE_URL + casts.getProfilePath())
                .placeholder(R.color.md_grey_200)
                .dontAnimate()
                .apply(new RequestOptions()).override(150, 150)
                .error(R.drawable.empty_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.cLoad.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.cLoad.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.castImg);

    }

    @Override
    public int getItemCount() {
        return castList == null ? 0 : castList.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView castImg,castEmpty;
        TextView pemain;
        ProgressBar cLoad;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castImg = itemView.findViewById(R.id.gbr_cast);
            pemain = itemView.findViewById(R.id.text_cast);
            cLoad = itemView.findViewById(R.id.casts_loading);
            castEmpty=itemView.findViewById(R.id.empty_cast);

        }
    }
}
