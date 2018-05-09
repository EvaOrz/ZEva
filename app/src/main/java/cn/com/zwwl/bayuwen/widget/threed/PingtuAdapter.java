package cn.com.zwwl.bayuwen.widget.threed;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.R;

public class PingtuAdapter extends PagerAdapter {

    private Context context;
    private int selectPos = 0;
    private List<View> viewList = new ArrayList<View>();

    public PingtuAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = new ImageView(context);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        gallery.removeView((View) object);
    }
}