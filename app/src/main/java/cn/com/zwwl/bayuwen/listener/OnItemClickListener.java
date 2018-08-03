package cn.com.zwwl.bayuwen.listener;

import android.view.View;

/**
 * Created by lousx
 */
public interface OnItemClickListener {

    void setOnItemClickListener(View view, int position);

    void setOnLongItemClickListener(View view, int position);
}
