package cn.com.zwwl.bayuwen.widget;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lousx on 2016/8/4.
 */
public class HSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;


    public HSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager)// 如果是最后一行，则不需要绘制底部
        {
            if ((parent.getAdapter().getItemCount() - 1) == parent.getChildAdapterPosition(view)) {
                outRect.right = 0;
            } else {
                outRect.right = space;
            }
        }

    }
}
