package cn.com.zwwl.bayuwen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.zwwl.bayuwen.model.RecommentModel;


public class FmHeadAdapter extends PagerAdapter {
    protected List<RecommentModel> list = new ArrayList<>();
    protected Context mContext;

    public FmHeadAdapter(Context context, List<RecommentModel> list) {
        this.mContext = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
            final RecommentModel recommentModel = list.get(position);

            return null;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
    }

}