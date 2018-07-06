package cn.com.zwwl.bayuwen.listener;


import android.widget.ListView;

import java.util.List;

import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 所有自定义view必须实现此接口,调用接口成功后实现此接口
 */
public interface ResponseCallBack<T> {
    void result(T t,ErrorMsg errorMsg);
}
