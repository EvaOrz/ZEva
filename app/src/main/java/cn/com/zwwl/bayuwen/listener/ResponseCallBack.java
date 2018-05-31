package cn.com.zwwl.bayuwen.listener;


import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 所有自定义view必须实现此接口,调用接口成功后实现此接口
 */
public interface ResponseCallBack<T> {
    public void success(T t);

    public void error(ErrorMsg error);

}
