package cn.bingoogolapple.photopicker.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/6/25 下午4:45
 * 描述:
 */
public class BGAPicassoImageLoader implements BGAImageLoader {

    @Override
    public void displayImage(final ImageView imageView, String path, @DrawableRes int loadingResId, @DrawableRes int failResId, int width, int height, final DisplayDelegate delegate) {
        if (path == null) {
            path = "";
        }

        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }

        final String finalPath = path;
        Picasso.with(imageView.getContext()).load(finalPath).placeholder(loadingResId).error(failResId).resize(width, height).centerInside().into(imageView, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                if (delegate != null) {
                    delegate.onSuccess(imageView, finalPath);
                }
            }
        });
    }

    @Override
    public void downloadImage(Context context, String path, final DownloadDelegate delegate) {
        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }

        final String finalPath = path;
        Picasso.with(context).load(finalPath).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (delegate != null) {
                    delegate.onSuccess(finalPath, bitmap);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (delegate != null) {
                    delegate.onFailed(finalPath);
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

}