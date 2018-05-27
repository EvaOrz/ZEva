package cn.com.zwwl.bayuwen.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by lousx on 2016/12/22.
 */

public class StopLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public StopLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
