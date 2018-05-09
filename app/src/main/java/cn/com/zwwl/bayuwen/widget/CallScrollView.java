package cn.com.zwwl.bayuwen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 兼容api23版本以下
 * 实现OnScrollListener功能
 */
public class CallScrollView extends ScrollView {
    private OnScrollListener listener;

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public CallScrollView(Context context) {
        super(context);
    }

    public CallScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CallScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener {
        void onScroll(boolean isUp);
    }

    /**
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (oldt > t && oldt - t > 40) {// 向下
            if (listener != null)
                listener.onScroll(false);
        } else if (oldt < t && t - oldt > 40) {// 向上
            if (listener != null)
                listener.onScroll(true);
        }

    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}