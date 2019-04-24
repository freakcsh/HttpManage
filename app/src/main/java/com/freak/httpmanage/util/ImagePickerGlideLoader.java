package com.freak.httpmanage.util;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

/**
 *
 * @author Administrator
 * @date 2018/4/10
 */

public class ImagePickerGlideLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(path).thumbnail(0.1f).into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(path).thumbnail(0.1f).into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
