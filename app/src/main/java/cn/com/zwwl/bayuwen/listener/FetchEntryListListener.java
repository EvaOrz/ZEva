package cn.com.zwwl.bayuwen.listener;


import java.util.List;

import cn.com.zwwl.bayuwen.model.ErrorMsg;

/**
 * 所有自定义view必须实现此接口,调用接口成功后实现此接口
 */
public interface FetchEntryListListener {
    /**
     * 给View传递数据
     *
     * @param list
     */
    void setData(List list);

    void setError(ErrorMsg error);

}
