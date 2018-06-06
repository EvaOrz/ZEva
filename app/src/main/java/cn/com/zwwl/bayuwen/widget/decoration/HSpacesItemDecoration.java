package cn.com.zwwl.bayuwen.widget.decoration;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.com.zwwl.bayuwen.util.DensityUtil;

/**
 * Created by lousx on 2016/8/4.
 */
public class HSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;


    public HSpacesItemDecoration(Resources resources,
                                 @DimenRes int size) {
        this.space = DensityUtil.dip2px(resources, size);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager)// 如果是最后一行，则不需要绘制底部
        {
            if ((parent.getAdapter().getItemCount() - 1) == parent.getChildAdapterPosition(view)) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = space;
            }
        }

    }
}
