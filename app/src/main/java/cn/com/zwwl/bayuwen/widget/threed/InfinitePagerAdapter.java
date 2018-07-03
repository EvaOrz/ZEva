package cn.com.zwwl.bayuwen.widget.threed;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A PagerAdapter that wraps around another PagerAdapter to handle paging wrap-around.
 */
public class InfinitePagerAdapter extends PagerAdapter {

    private List<View> list;

    public InfinitePagerAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (getRealCount() == 0) {
            return 0;
        }
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        return list.size();
    }

    /**
     * @return the {@link #getCount()} result of the wrapped adapter
     */
    public int getRealCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        return list.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}