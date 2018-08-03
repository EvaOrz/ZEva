package cn.com.zwwl.bayuwen.listener;


import cn.com.zwwl.bayuwen.model.Entry;
import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 所有自定义view必须实现此接口,调用接口成功后实现此接口
 */
public interface FetchEntryListener {
    /**
     * 给View传递数据
     *
     * @param entry
     */
    public void setData(Entry entry);

    public void setError(ErrorMsg error);

}
