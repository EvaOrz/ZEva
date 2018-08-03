package cn.com.zwwl.bayuwen.listener;

/**
 * 读取网络数据之后的回调接口
 * 
 *
 */
public interface FetchDataListener {
	public void fetchData(boolean isSuccess, String data, boolean fromHttp);
}
