package cn.com.zwwl.bayuwen.widget.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lousx on 2016/8/4.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int leftSpace;
    private int rightSpace;
    private int bottomSpace;
    private int topSpace;


    public SpacesItemDecoration(int space) {
        this.space = space;
    }
    public SpacesItemDecoration(int leftSpace, int rightSpace, int bottomSpace, int topSpace) {
        this.leftSpace = leftSpace;
        this.rightSpace = rightSpace;
        this.topSpace = topSpace;
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (space != 0) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = space;
            }
        }else{
            outRect.left = leftSpace;
            outRect.right = rightSpace;
            outRect.bottom = bottomSpace;
            outRect.top = topSpace;
        }
    }
}
