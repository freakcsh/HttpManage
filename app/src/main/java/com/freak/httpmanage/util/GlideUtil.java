package com.freak.httpmanage.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.freak.httpmanage.R;


public class GlideUtil {
    private static GlideUtil glideUtil;

    public static GlideUtil getInstance() {
        if (glideUtil == null) {
            synchronized (GlideUtil.class) {
                if (glideUtil == null) {
                    glideUtil = new GlideUtil();
                }
            }
        }
        return glideUtil;
    }

    public void loadImage(Context context, ImageView imageView, String url) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.glide_loading)
                .error(R.drawable.ic_icon_glide_error)
                .thumbnail(0.1f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(imageView);
    }

    public void loadImageSquare(Context context, ImageView imageView, String url) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.glide_loading)
                .error(R.drawable.ic_icon_glide_error)
                .thumbnail(0.1f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .fitCenter()
                .into(imageView);
    }
    public void loadImageCamera(Context context, ImageView imageView, String url) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.glide_loading)
                .error(R.drawable.ic_icon_glide_error)
                .thumbnail(0.1f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .fitCenter()
                .into(imageView);
    }
    public void loadImageCamera1(Context context, ImageView imageView, String url) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.glide_loading)
                .error(R.drawable.ic_icon_glide_error)
                .thumbnail(0.1f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .fitCenter()
                .into(imageView);
    }
    public void loadImageUserPhoto(Context context, ImageView imageView, String url) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.glide_loading)
                .error(R.drawable.ic_icon_glide_error)
                .thumbnail(0.1f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(imageView);
    }

}
