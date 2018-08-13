package cn.com.zwwl.bayuwen.widget.photoview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;

import cn.com.zwwl.bayuwen.glide.ImageLoader;

public class PhotoAdapter extends PagerAdapter {
    private String[]  imagesUrl;
    private Context context;

    PhotoAdapter(String[] imagesUrl, Context context) {
        this.imagesUrl = imagesUrl;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (imagesUrl == null || imagesUrl.length == 0) ? 0 : imagesUrl.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        String url = imagesUrl[position];

        PhotoView photoView = new PhotoView(context);
        ImageLoader.displayPhoto(context, photoView, url);
        container.addView(photoView);
        return photoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
