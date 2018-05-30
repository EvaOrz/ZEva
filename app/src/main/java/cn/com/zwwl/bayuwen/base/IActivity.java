package cn.com.zwwl.bayuwen.base;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * @author Monty
 *         created at 2017/6/7 16:32
 */

public interface IActivity {
    void setDisplayShowCustomTitleEnabled(boolean showTitleEnabled);

    void setDisplayHomeAsUpEnabled(boolean showHomeEnabled);

    void setDisplayShowTitleEnabled(boolean showTitleEnabled);


    void setContentView(@LayoutRes final int layoutResID);

    void setContentView(View view);

    void setCustomTitle(CharSequence title);

    void setCustomTitle(int title);

    void setUpIndicator(int resId);

    void showMenu(int menuCode);
}
