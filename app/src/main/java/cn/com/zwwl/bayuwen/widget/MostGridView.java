package cn.com.zwwl.bayuwen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by lousx on 2018/5/11.
 */

public class MostGridView extends GridView {
    public MostGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MostGridView(Context context) {
        super(context);
    }

    public MostGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
